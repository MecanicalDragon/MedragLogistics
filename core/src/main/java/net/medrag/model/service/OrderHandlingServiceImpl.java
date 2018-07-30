package net.medrag.model.service;

import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.*;
import net.medrag.model.service.dto.CargoService;
import net.medrag.model.service.dto.OrderService;
import net.medrag.model.service.dto.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class OrderHandlingServiceImpl implements OrderHandlingService {

    private IndexService indexService;

    private CargoService<CargoDto, Cargo> cargoService;

    private OrderService<OrderrDto, Orderr> orderService;

    private MailService mailService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

    private RabbitService rabbitService;

    @Autowired
    public void setRabbitService(RabbitService rabbitService) {
        this.rabbitService = rabbitService;
    }


    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setOrderService(OrderService<OrderrDto, Orderr> orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @Override
    @Transactional
    public OrderrDto compileOrder(List<CargoDto> cargoList, CustomerDto customer) throws MedragServiceException {

        OrderrDto order = new OrderrDto();
        order.setOwner(customer);
        order.setIndex(indexService.indicate(order));
        order.setComplete(false);

        Integer idOrder = orderService.addDto(order, new Orderr());
        order.setId(idOrder);

        for (CargoDto cargoDto : cargoList) {
            cargoDto.setOwner(customer);
            cargoDto.setState("TRANSIENT");
            cargoDto.setOrderr(order);
            cargoDto.setIndex(indexService.indicate(cargoDto));

            Integer id = cargoService.addDto(cargoDto, new Cargo());
            cargoDto.setId(id);

            rabbitService.sendCargo(cargoDto);
        }

        order.setCargoes(cargoList);

        if (order.getOwner().getEmail() != null) {
            try {
                mailService.sendTakenOrderMail(order);
            } catch (MessagingException e) {
                throw new MedragServiceException(e);
            }
        }
        return order;
    }

    @Override
    @Transactional
    public void deliverCargo(CargoDto deliveredCargo) throws MedragServiceException {

        deliveredCargo.setState("DELIVERED");
        for (WaypointDto waypoint : deliveredCargo.getRoute()){
                waypointService.removeDto(waypoint, new Waypoint());
            }
        cargoService.updateDtoStatus(deliveredCargo, new Cargo());
        List<CargoDto> orderCargoes = deliveredCargo.getOrderr().getCargoes();
        int deliveredCargoes = 0;
        for (CargoDto orderCargo : orderCargoes) {
            if (orderCargo.getState().equals("DELIVERED")) {
                deliveredCargoes++;
            }
        }

        if (deliveredCargoes == orderCargoes.size()) {
            deliveredCargo.getOrderr().setComplete(true);

            cargoService.updateDtoStatus(deliveredCargo, new Cargo());

        }

        rabbitService.sendCargo(deliveredCargo);
    }
}

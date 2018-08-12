package net.medrag.model.service;

import net.medrag.model.domain.dto.CargoDto;
import net.medrag.model.domain.dto.CustomerDto;
import net.medrag.model.domain.dto.OrderrDto;
import net.medrag.model.domain.dto.WaypointDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.service.dto.CargoService;
import net.medrag.model.service.dto.OrderService;
import net.medrag.model.service.dto.WaypointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class OrderHandlingServiceImpl implements OrderHandlingService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHandlingServiceImpl.class);

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
        for (WaypointDto waypoint : deliveredCargo.getRoute()) {
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

    @PostConstruct
    public void clearCompletedOrders() {

        Thread thread = new Thread(() -> {

            while (!Thread.currentThread().isInterrupted()) {

//                If this is a first day of month, set workedTime and paidTime to zeroes, set lastMonthHours for each driver
                ZonedDateTime now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow"));
                if (now.getDayOfMonth() == 1) {
                    try {
                        List<OrderrDto> orders = orderService.getDtoList(new OrderrDto(), new Orderr(), "COMPLETE", "true");
                        int size = orders.size() / 2;
                        for (int i = 0; i < size; i++) {
                            orderService.removeDto(orders.get(i), new Orderr());
                        }

                    } catch (MedragServiceException e) {
                        LOGGER.error("Error while nulling DriverWorkedHours in DriverPaidHoursNullerThread: {}", e);
                    }
                }

//                Sleep until next day will come
                now = ZonedDateTime.ofInstant(Instant.now(), ZoneId.of("Europe/Moscow"));

                int h = now.getHour() * 3600;
                int m = now.getMinute() * 60;
                int s = now.getSecond();
                try {
                    TimeUnit.SECONDS.sleep(86400 - h - m - s + 1);
                } catch (InterruptedException e) {
                    LOGGER.error("Error while sleeping in ClearCompletedOrdersThread: {}", e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}

package net.medrag.model.service;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.dto.OrderrDto;
import net.medrag.dto.WaypointDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.domain.entity.Waypoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class OrderCompilingServiceImpl implements OrderCompilingService{

    private IndexService indexService;

    private CargoService<CargoDto, Cargo> cargoService;

    private OrderService<OrderrDto, Orderr> orderService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

    @Autowired
    public void setOrderService(OrderService<OrderrDto, Orderr> orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setIndexService(IndexService indexService) {
        this.indexService = indexService;
    }

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }


    @Override
    @Transactional
    public OrderrDto compileOrder(List<CargoDto> cargoList, CustomerDto customer) {

        OrderrDto order = new OrderrDto();
        order.setOwner(customer);
        order.setOrderIndex(indexService.indicate(order));
        order.setImplemented(false);

        Integer idOrder = orderService.addDto(order, new Orderr());
        order.setId(idOrder);

        for (CargoDto cargoDto : cargoList) {
            cargoDto.setOwner(customer);
            cargoDto.setState("PREPARED");
            cargoDto.setCargoIndex(indexService.indicate(cargoDto));

            Integer id = cargoService.addDto(cargoDto, new Cargo());
            cargoDto.setId(id);

            WaypointDto waypointLoad = new WaypointDto();
            waypointLoad.setCargo(cargoDto);
            waypointLoad.setCity(cargoDto.getDeparture());
            waypointLoad.setOrderr(order);
            waypointLoad.setWayPointType("LOAD");
            waypointService.addDto(waypointLoad, new Waypoint());

            WaypointDto waypointUnload = new WaypointDto();
            waypointUnload.setCargo(cargoDto);
            waypointUnload.setCity(cargoDto.getDestination());
            waypointUnload.setOrderr(order);
            waypointUnload.setWayPointType("UNLOAD");
            waypointService.addDto(waypointUnload, new Waypoint());

        }

        return orderService.getDtoById(order, new Orderr(), idOrder);
    }
}

package net.medrag.model.service;

import net.medrag.dto.*;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.service.dto.CargoService;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.OrderService;
import net.medrag.model.service.dto.WaypointService;
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

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
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
        order.setIndex(indexService.indicate(order));
        order.setImplemented(false);

        Integer idOrder = orderService.addDto(order, new Orderr());
        order.setId(idOrder);

        for (CargoDto cargoDto : cargoList) {
            cargoDto.setOwner(customer);
            cargoDto.setState("PREPARED");
            cargoDto.setIndex(indexService.indicate(cargoDto));

            Integer id = cargoService.addDto(cargoDto, new Cargo());
            cargoDto.setId(id);

            CityDto departure = cityService.getDtoByNaturalId(new CityDto(), new City(), cargoDto.getDepartureName());
            CityDto destination = cityService.getDtoByNaturalId(new CityDto(), new City(), cargoDto.getDestinationName());

            WaypointDto waypointLoad = new WaypointDto();
            waypointLoad.setCargo(cargoDto);
            waypointLoad.setCity(departure);
            waypointLoad.setOrderr(order);
            waypointLoad.setWayPointType("LOAD");
            waypointService.addDto(waypointLoad, new Waypoint());

            WaypointDto waypointUnload = new WaypointDto();
            waypointUnload.setCargo(cargoDto);
            waypointUnload.setCity(destination);
            waypointUnload.setOrderr(order);
            waypointUnload.setWayPointType("UNLOAD");
            waypointService.addDto(waypointUnload, new Waypoint());

        }

        return orderService.getDtoById(order, new Orderr(), idOrder);
    }
}

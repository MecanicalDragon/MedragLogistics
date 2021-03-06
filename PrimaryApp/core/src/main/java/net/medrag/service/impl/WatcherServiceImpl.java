package net.medrag.service.impl;

import net.medrag.domain.entity.Cargo;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CargoForm;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.RabbitService;
import net.medrag.service.api.WatcherService;
import net.medrag.service.dto.api.CargoService;
import net.medrag.service.dto.api.DriverService;
import net.medrag.service.dto.api.TruckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service, that handles Watcher app requests and works with it.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class WatcherServiceImpl implements WatcherService {

    private CargoService<CargoDto, Cargo> cargoService;

    private RabbitService rabbit;

    private DriverService<DriverDto, Driver> driverService;

    private TruckService<TruckDto, Truck> truckService;

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setRabbit(RabbitService rabbit) {
        this.rabbit = rabbit;
    }

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    /**
     * Method sends list of last cargoes to the Watcher App.
     *
     * @return - last 10 cargoes list.
     * @throws MedragServiceException - it's not.
     */
    @Override
    public List<CargoForm> getCargoesList() throws MedragServiceException {

        List<CargoDto>cargoDtoList = cargoService.getLastObjects(new CargoDto(), new Cargo(), 10);
        List<CargoForm> cargoformList = new ArrayList<>();
        for (CargoDto c : cargoDtoList) {
            CargoForm d = new ModelMapper().map(c, CargoForm.class);
            cargoformList.add(d);
        }
        rabbit.run();    // Run, rabbit, run!

        return cargoformList;
    }

    /**
     * Method sends information about drivers and trucks to Watcher App.
     *
     * @return - information about drivers and trucks.
     * @throws MedragServiceException - as usual.
     */
    @Override
    public Integer[] getStats() throws MedragServiceException {

        Integer workingDrivers = driverService.getDtoCount(new Driver(), "STATE", "'PORTER'", "STATE", "'ON_SHIFT'", "STATE", "'DRIVING'");
        Integer readyDrivers = driverService.getDtoCount(new Driver(), "STATE", "'READY_TO_ROUTE'");
        Integer restingDrivers = driverService.getDtoCount(new Driver(), "STATE", "'REST'");

        Integer trucksInUse = truckService.getDtoCount(new Truck(), "STATUS", "'IN_USE'");
        Integer trucksStayingIdle = truckService.getDtoCount(new Truck(), "STATUS", "'STAY_IDLE'");
        Integer trucksInService = truckService.getDtoCount(new Truck(), "STATUS", "'IN_SERVICE'");

        Integer[]statistic = new Integer[6];

        statistic[0] = workingDrivers;
        statistic[1] = readyDrivers;
        statistic[2] = restingDrivers;
        statistic[3] = trucksInUse;
        statistic[4] = trucksStayingIdle;
        statistic[5] = trucksInService;

        return statistic;
    }
}

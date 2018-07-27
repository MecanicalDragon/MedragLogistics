package net.medrag.model.service;

import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CargoForm;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.service.dto.CargoService;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.TruckService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link}
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

    @Override
    public List<CargoForm> getCargoesList() throws MedragServiceException {

        List<CargoDto>cargoDtoList = cargoService.getDtoList(new CargoDto(), new Cargo(), "ORDER BY id DESC%20");
        List<CargoForm> cargoformList = new ArrayList<>();
        for (CargoDto c : cargoDtoList) {
            CargoForm d = new ModelMapper().map(c, CargoForm.class);
            cargoformList.add(d);
        }
        rabbit.run();    // Run, rabbit, run!

        return cargoformList;
    }

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

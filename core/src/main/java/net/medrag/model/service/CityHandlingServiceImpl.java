package net.medrag.model.service;

import net.medrag.model.domain.dto.CargoDto;
import net.medrag.model.domain.dto.CityDto;
import net.medrag.model.domain.dto.DriverDto;
import net.medrag.model.domain.dto.TruckDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.service.dto.CargoService;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityHandlingServiceImpl implements CityHandlingService {

    private CargoService<CargoDto, Cargo> cargoService;

    private DriverService<DriverDto, Driver> driverService;

    private TruckService<TruckDto, Truck> truckService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    @Override
    public boolean removeCity(CityDto city) throws MedragServiceException {

        List<CargoDto> departures = cargoService.getDtoList(new CargoDto(), new Cargo(), "DEPARTURE_ID", city.getId().toString());
        List<CargoDto> destinations = cargoService.getDtoList(new CargoDto(), new Cargo(), "DESTINATION_ID", city.getId().toString());
        List<CargoDto> currents = cargoService.getDtoList(new CargoDto(), new Cargo(), "CURRENT_CITY_ID", city.getId().toString());

        List<DriverDto> drivers = driverService.getDtoList(new DriverDto(), new Driver(), "CURRENT_CITY_ID", city.getId().toString());
        List<DriverDto> driversDest = driverService.getDtoList(new DriverDto(), new Driver(), "DESTINATION_CITY_ID", city.getId().toString());

        List<TruckDto> trucks = truckService.getDtoList(new TruckDto(), new Truck(),"CURRENT_CITY_ID", city.getId().toString());
        List<TruckDto> trucksDest = truckService.getDtoList(new TruckDto(), new Truck(),"DESTINATION_CITY_ID", city.getId().toString());

        return departures.size() + destinations.size() + currents.size() + drivers.size() + driversDest.size() + trucks.size() + trucksDest.size() == 0;
    }
}

package net.medrag.controller.logistic;

import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.*;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.DriverService;
import net.medrag.model.service.dto.TruckService;
import net.medrag.model.service.dto.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This controller does all work about logistic management of cargoes. Details in methods.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-wp")
public class LogisticWizardController {

    private TruckService<TruckDto, Truck> truckService;

    private DriverService<DriverDto, Driver> driverService;

    private CityService<CityDto, City> cityService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    /**
     * First method in logistic wizard. Main goal is getting a list of trucks in the city of chosen cargoes.
     *
     * @param index - index of chosen cargo in session global cargo list
     */
    @GetMapping("chooseTruck/{index}")
    public String initializeTruck(@PathVariable Integer index, HttpServletRequest request){

//        Removing cargo with index 'index' from globalCargoes list
        List<CargoDto> cargoList = (List<CargoDto>)request.getSession().getAttribute("globalCargoes");
        CargoDto cargo = cargoList.remove(index.intValue());

//        Getting list of trucks and filtering it with the requirements of capacity, status and city dislocation
        List<TruckDto>truckDtoList = truckService.getDtoList(new TruckDto(), new Truck());
        List<TruckDto> filteredTruckList = new ArrayList<>();
        for (TruckDto truckDto : truckDtoList) {
            if(truckDto.getCityName().equals(cargo.getCurrentCityName()) &&
                    truckDto.getStatus().equals("STAY_IDLE") &&
                    Integer.valueOf(truckDto.getCapacity()) >= Integer.valueOf(cargo.getWeight())){
                filteredTruckList.add(truckDto);
            }
        }

//        Creating list of cargoes in the chosen truck and adding there a first cargo
        List<CargoDto> truckLoad = new ArrayList<>();
        truckLoad.add(cargo);

//        Adding new attributes to session
        request.getSession().setAttribute("truckLoad", truckLoad);
        request.getSession().setAttribute("trucksInCity", filteredTruckList);
        request.getSession().setAttribute("currentWeight", cargo.getWeight());

        return "logistic/chooseTruck";
    }

    /**
     * Second step: adding attribute of chosen truck and getting from session list of global cargoes list
     * of cargoes in the chosen city.
     *
     * @param index - index of chosen truck in session city trucks list
     */
    @GetMapping("confirmTruck/{index}")
    public String clarify(@PathVariable Integer index, HttpServletRequest request){

//        Adding attribute of chosen truck in session and setting list of city trucks to null
        List<TruckDto>truckList = (List<TruckDto>)request.getSession().getAttribute("trucksInCity");
        TruckDto chosenTruck = truckList.get(index);
        request.getSession().setAttribute("chosenTruck", chosenTruck);
        request.getSession().setAttribute("trucksInCity", null);

//        Filtering global cargoes list to cargoes in city list
        List<CargoDto> cargoList = (List<CargoDto>)request.getSession().getAttribute("globalCargoes");
        List<CargoDto> filteredCargoList = new ArrayList<>();
        for (CargoDto cargoDto : cargoList) {
            if (cargoDto.getCurrentCityName().equals(chosenTruck.getCityName())){
                filteredCargoList.add(cargoDto);
            }
        }

//        Adding to session new attribute of city cargoes list and nulling global cargoes.
        request.getSession().setAttribute("cityCargoes", filteredCargoList);
        request.getSession().setAttribute("globalCargoes", null);

        return "logistic/addCargoes";
    }

    /**
     * Third step - cyclic: adding next cargo to the truck.
     *
     * @param index - index of chosen cargo in city cargoes list
     */
    @GetMapping("addCargo/{index}")
    public String addCargo(@PathVariable Integer index, HttpServletRequest request) {

//        Transfer next cargo from list of city cargoes to truck cargoes list
        List<CargoDto> truckLoad = (List<CargoDto>)request.getSession().getAttribute("truckLoad");
        List<CargoDto> cityCargoes = (List<CargoDto>)request.getSession().getAttribute("cityCargoes");
        truckLoad.add(cityCargoes.remove(index.intValue()));

//        Setting new currentWeight variable
        int currentWeight = 0;
        for (CargoDto cargo : truckLoad) {
            currentWeight += Integer.valueOf(cargo.getWeight());
        }
        request.getSession().setAttribute("currentWeight", currentWeight);

        return "logistic/addCargoes";

    }

    /**
     * Step number four: choosing a city of next waypoint. Nothing interesting here.
     */
    @GetMapping("chooseCity")
    public String chooseCity(HttpServletRequest request){
        List<CityDto> cities = cityService.getDtoList(new CityDto(), new City());
        request.getSession().setAttribute("cities", cities);
        return "logistic/chooseCity";
    }

    /**
     * Fifth step: getting and assigning to the route drivers.
     */
    @GetMapping("confirmCity/{city}")
    public String addDrivers(@PathVariable Integer city, HttpServletRequest request){
        List<CityDto>cities = (List<CityDto>)request.getSession().getAttribute("cities");
        CityDto destinationCity = cities.get(city);
        request.getSession().setAttribute("destinationCity", destinationCity);
        request.getSession().setAttribute("cities", null);
        TruckDto chosenTruck = (TruckDto)request.getSession().getAttribute("chosenTruck");
        List<DriverDto> drivers = driverService.getDtoList(new DriverDto(), new Driver());
        List<DriverDto> filteredDrivers = new ArrayList<>();
        for (DriverDto driver : drivers) {
            if (driver.getCityName().equals(chosenTruck.getCityName()) &&
            driver.getState().equals("READY_TO_ROUTE")){
                filteredDrivers.add(driver);
            }
        }
        request.getSession().setAttribute("drivers", filteredDrivers);
        request.getSession().setAttribute("brigade", chosenTruck.getBrigadeStr());

        return "logistic/addDrivers";
    }

    /**
     * Final step: compiling waypoints.
     *
     * @param drivers - indexes of assigned drivers in the session attribute list drivers.
     */
    @PostMapping("compileWP")
    public String compileWP(@RequestParam("drivers") String drivers, HttpServletRequest request){

        List<DriverDto>driverList = (List<DriverDto>) request.getSession().getAttribute("drivers");
        List<CargoDto> truckLoad = (List<CargoDto>) request.getSession().getAttribute("truckLoad");
        TruckDto assignedTruck = (TruckDto) request.getSession().getAttribute("chosenTruck");
        CityDto destinationCity = (CityDto) request.getSession().getAttribute("destinationCity");

        Set<DriverDto> brigade = new HashSet<>();
        String[] split = drivers.split("/");
        for (String s : split)   {
            brigade.add(driverList.get(Integer.valueOf(s)));
        }

        CityDto currentCity = cityService.getDtoByNaturalId(new CityDto(), new City(), assignedTruck.getCityName());

        for ( CargoDto cargo : truckLoad){

            cargo.setState("PREPARED");
            for (DriverDto driverDto : brigade) {
                driverDto.setState("ON_SHIFT");
            }
            assignedTruck.setStatus("IN_USE");
            assignedTruck.setBrigade(brigade);

            WaypointDto load = new WaypointDto();
            load.setCity(currentCity);
            load.setWayPointType("LOAD");
            load.setComplete("false");
            load.setCargo(cargo);
            load.setOrderr(cargo.getOrderr());
            load.setCurrentTruck(assignedTruck);
            load.setBrigade(brigade);

            waypointService.compileRoute(load, destinationCity);

        }

        return "redirect: ../mgr-main";
    }

}

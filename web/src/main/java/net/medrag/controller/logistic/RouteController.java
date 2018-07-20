package net.medrag.controller.logistic;

import net.medrag.model.dto.*;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.RouteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * This controller compiles waypoints, adds it to the database and returns the main page.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-compileRoute")
public class RouteController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RouteController.class);

    private RouteService routeService;

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * Final step: compiling waypoints. Successful result of this method returns to the main page with added to
     * the database waypoints for chosen cargoes
     *
     * @param drivers - indexes of assigned drivers in the session attribute list drivers.
     */
    @PostMapping
    public String compileWP(@RequestParam("drivers") String drivers, HttpServletRequest request) throws MedragServiceException{

//        Getting attributes from session
        List<DriverDto> driverList = (List<DriverDto>) request.getSession().getAttribute("drivers");
        List<CargoDto> truckLoad = (List<CargoDto>) request.getSession().getAttribute("truckLoad");
        TruckDto assignedTruck = (TruckDto) request.getSession().getAttribute("chosenTruck");
        CityDto destinationCity = (CityDto) request.getSession().getAttribute("destinationCity");
        CityDto departureCity = (CityDto) request.getSession().getAttribute("departureCity");

//        Creating brigade driver set
        Set<DriverDto> brigade = new HashSet<>();
        String[] split = drivers.split("/");
        for (String s : split) {
            brigade.add(driverList.get(Integer.valueOf(s)));
        }

//        Setting new statuses to truck and drivers
        for (DriverDto driverDto : brigade) {
            driverDto.setState("PORTER");
            driverDto.setCurrentTruck(assignedTruck);
        }
        assignedTruck.setStatus("IN_USE");
        assignedTruck.setBrigade(brigade);

//        Adding waypoints for every cargo in truckload
        for (CargoDto cargo : truckLoad) {
            cargo.setState("PREPARED");
            WaypointDto load = new WaypointDto();
            load.setCity(departureCity);
            load.setWayPointType("LOAD");
            load.setComplete("false");
            load.setCargo(cargo);
            load.setOrderr(cargo.getOrderr());
            load.setCurrentTruck(assignedTruck);
            load.setBrigade(brigade);

//            Transactional method in waypoint service
            routeService.compileRoute(load, destinationCity);
        }

        return "redirect: ../mgr-main";
    }

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {
        LOGGER.error("MedragServiceException happened: {}", ex);

        return "public/error";

    }

}

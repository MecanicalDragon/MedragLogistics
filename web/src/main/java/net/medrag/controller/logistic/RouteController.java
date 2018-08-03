package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
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
    public String compileWP(@RequestParam("drivers") String drivers, HttpServletRequest request) throws MedragControllerException {

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

//        Transactional method in waypoint service
        try {
            routeService.compileRoute(departureCity, destinationCity, truckLoad, assignedTruck, brigade);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../mgr-main";
    }
}

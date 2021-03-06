package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
     * @param request - request
     * @return - redirects to mgr-main
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping
    public String compileWP(@RequestParam("drivers") String drivers, HttpServletRequest request,
                            RedirectAttributes redirect) throws MedragControllerException {

//        Getting attributes from session
        List<DriverDto> driverList = (List<DriverDto>) request.getSession().getAttribute("drivers");
        List<CargoDto> truckLoad = (List<CargoDto>) request.getSession().getAttribute("truckLoad");
        TruckDto assignedTruck = (TruckDto) request.getSession().getAttribute("chosenTruck");
        CityDto destinationCity = (CityDto) request.getSession().getAttribute("destinationCity");
        CityDto departureCity = (CityDto) request.getSession().getAttribute("departureCity");

//        Creating brigade driver set
        List<DriverDto> brigade = new ArrayList<>();
        String[] split = drivers.split("/");
        for (String s : split) {
            brigade.add(driverList.get(Integer.valueOf(s)));
        }

//        Transactional method in waypoint service
        try {
            routeService.compileRoute(departureCity, destinationCity, truckLoad, assignedTruck, brigade);
            redirect.addFlashAttribute("newRoute", true);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect:/mgr-main";

    }

    /**
     * Compiling new route for rerouted truck
     *
     * @param index - index of destination city in session city list
     * @param currentBrigade - trying to save current brigade boolean
     * @param request - request
     * @return - redirects to mgr-route
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("uncompleted")
    public String compileUncompleted(@RequestParam Integer index, @RequestParam Boolean currentBrigade,
                                     HttpServletRequest request, RedirectAttributes redirect) throws MedragControllerException {

        List<CityDto> cities = (List<CityDto>) request.getSession().getAttribute("cities");
        CityDto destinationCity = cities.get(index);
        CityDto departureCity = (CityDto) request.getSession().getAttribute("departureCity");

//        Getting attributes from session
        List<CargoDto> truckLoad = (List<CargoDto>) request.getSession().getAttribute("newTruckLoad");
        TruckDto assignedTruck = (TruckDto) request.getSession().getAttribute("chosenTruck");

//        Transactional method in waypoint service
        try {
            routeService.compileUncompletedRoute(departureCity, destinationCity, truckLoad, assignedTruck, currentBrigade);
            redirect.addFlashAttribute("newRoute", true);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        return "redirect:/../mgr-route";

    }

    /**
     * Assigning new brigade to earlier rerouted truck
     *
     * @param drivers - new brigade
     * @param request - request
     * @return - redirects to mgr-route
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("complete")
    public String completeRoute(@RequestParam("drivers") String drivers, HttpServletRequest request,
                                RedirectAttributes redirect) throws MedragControllerException {

//        Getting attributes from session
        TruckDto assignedTruck = (TruckDto) request.getSession().getAttribute("chosenTruck");
        List<DriverDto>driverList = (List<DriverDto>)request.getSession().getAttribute("drivers");

//        Creating brigade driver set
        List<DriverDto> brigade = new ArrayList<>();
        String[] split = drivers.split("/");
        for (String s : split) {
            brigade.add(driverList.get(Integer.valueOf(s)));
        }

//        Transactional method in waypoint service
        try {
            routeService.compileRouteForTruck(assignedTruck, brigade);
            redirect.addFlashAttribute("newRoute", true);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect:/../mgr-route";
    }

}

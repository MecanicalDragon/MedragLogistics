package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.DriverDto;
import net.medrag.service.api.DirectionsService;
import net.medrag.service.api.DriverHandlerService;
import net.medrag.service.MedragServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * This controller handles addDrivers.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-assignDrivers")
public class AddingDriversController {

    private DriverHandlerService driverHandlerService;

    private DirectionsService directionsService;

    @Autowired
    public void setDirectionsService(DirectionsService directionsService) {
        this.directionsService = directionsService;
    }

    @Autowired
    public void setDriverHandlerService(DriverHandlerService driverHandlerService) {
        this.driverHandlerService = driverHandlerService;
    }

    /**
     * Fourth step: getting and assigning drivers to the route.
     * Finish it with the {@link RouteController}
     */
    @PostMapping
    public String addDrivers(@RequestParam Integer index, HttpServletRequest request, Model model) throws MedragControllerException {

//        Defining cities
        List<CityDto> cities = (List<CityDto>) request.getSession().getAttribute("cities");
        CityDto destinationCity = cities.get(index);
        CityDto departureCity = (CityDto) request.getSession().getAttribute("departureCity");

//        Get the trip distance and time, needed for that trip
        Integer[] trip;
        try {
            trip = directionsService.getTripTime(departureCity, destinationCity);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

//        Get the list of available drivers
        List<DriverDto> drivers;
        try {
            drivers = driverHandlerService.getDriverList(departureCity.getId(), trip[1]);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

//        Adding attributes
        model.addAttribute("distance", trip[0]);
        model.addAttribute("duration", trip[1]);
        request.getSession().setAttribute("destinationCity", destinationCity);
        request.getSession().setAttribute("drivers", drivers);

        return "logistic/addDrivers";
    }

}

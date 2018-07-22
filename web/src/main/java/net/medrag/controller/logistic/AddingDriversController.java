package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.service.DirectionsService;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * This controller handles addDrivers.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-destination")
public class AddingDriversController {

    private DriverService<DriverDto, Driver> driverService;

    private DirectionsService directionsService;

    @Autowired
    public void setDirectionsService(DirectionsService directionsService) {
        this.directionsService = directionsService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }


    /**
     * Fourth step: getting and assigning drivers to the route.
     * Finish it with the {@link RouteController}
     */
    @PostMapping
    public String addDrivers(@RequestParam Integer index, HttpServletRequest request, Model model)throws MedragControllerException {

//        Getting list of drivers
        TruckDto chosenTruck = (TruckDto) request.getSession().getAttribute("chosenTruck");
        List<DriverDto> drivers = null;
        try {
            drivers = driverService.getDtoList(new DriverDto(), new Driver(),
                    "CURRENT_CITY_ID", chosenTruck.getCityId().toString(), "STATE", "'READY_TO_ROUTE'");
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        List<DriverDto> filteredDrivers = new ArrayList<>();

//        Defining cities
        List<CityDto> cities = (List<CityDto>) request.getSession().getAttribute("cities");
        CityDto destinationCity = cities.get(index);
        CityDto departureCity = (CityDto) request.getSession().getAttribute("departureCity");

//        Filtering drivers by the worked time
        Integer[] trip = new Integer[0];
        try {
            trip = directionsService.getTripTime(departureCity, destinationCity);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        for (DriverDto driver : drivers){
            if (driver.getWorkedTime()+trip[1] <= 10560){
                filteredDrivers.add(driver);
            }
        }

//        Adding attributes
        model.addAttribute("distance", trip[0]);
        model.addAttribute("duration", trip[1]);
        request.getSession().setAttribute("destinationCity", destinationCity);
        request.getSession().setAttribute("drivers", filteredDrivers);
        request.getSession().setAttribute("brigade", chosenTruck.getBrigadeStr());

        return "logistic/addDrivers";
    }

}

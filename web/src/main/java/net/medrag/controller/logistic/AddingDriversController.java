package net.medrag.controller.logistic;

import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.DriverService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
@RequestMapping("mgr-destination")
public class AddingDriversController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddingDriversController.class);


    private DriverService<DriverDto, Driver> driverService;

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
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
    public String addDrivers(@RequestParam Integer index, HttpServletRequest request)throws MedragServiceException {

//        Getting list of drivers
        TruckDto chosenTruck = (TruckDto) request.getSession().getAttribute("chosenTruck");
        List<DriverDto> drivers = driverService.getDtoList(new DriverDto(), new Driver(),
                "CURRENT_CITY_ID", chosenTruck.getCityId().toString(), "STATE", "'READY_TO_ROUTE'");

//        Defining cities
        List<CityDto> cities = (List<CityDto>) request.getSession().getAttribute("cities");
        CityDto destinationCity = cities.get(index);
        CityDto departureCity = cityService.getDtoById(new CityDto(), new City(), chosenTruck.getCityId());

//        Adding attributes
        request.getSession().setAttribute("destinationCity", destinationCity);
        request.getSession().setAttribute("departureCity", departureCity);
        request.getSession().setAttribute("drivers", drivers);
        request.getSession().setAttribute("brigade", chosenTruck.getBrigadeStr());

        return "logistic/addDrivers";
    }

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {
        LOGGER.error("MedragServiceException happened: {}", ex);

        return "public/error";

    }
}

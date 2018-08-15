package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.dto.WaypointDto;
import net.medrag.domain.entity.Cargo;
import net.medrag.domain.entity.Waypoint;
import net.medrag.service.api.DirectionsService;
import net.medrag.service.api.DriverHandlerService;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.CargoService;
import net.medrag.service.dto.api.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("mgr-rerouteTruck")
public class ManageCargoesController {

    private CargoService<CargoDto, Cargo> cargoService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

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

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @PostMapping
    public String getCargoes(HttpServletRequest request, @RequestParam Integer index, Model model) throws MedragControllerException {

        List<TruckDto> trucks = (List<TruckDto>) request.getSession().getAttribute("trucksInUse");
        TruckDto chosenTruck = trucks.get(index);

        if (chosenTruck.getManageable().equals("TRUE")) {
            try {
                List<CargoDto> cargoes = cargoService.getDtoList(new CargoDto(), new Cargo(), "CURRENT_CITY_ID",
                        chosenTruck.getDestinationId().toString(), "STATE", "'TRANSIENT'");
                List<WaypointDto> wps = waypointService.getDtoList(new WaypointDto(), new Waypoint(), "CITY_ID",
                        chosenTruck.getDestinationId().toString(), "TRUCK_ID", chosenTruck.getId().toString(), "WP_TYPE",
                        "'UNLOAD'", "COMPLETE", "false");
                List<CargoDto> cargoesInTruck = wps.stream().map(WaypointDto::getCargo).collect(Collectors.toList());

                cargoes.addAll(0, cargoesInTruck);
                Integer truckLoadCount = cargoesInTruck.size();

                request.getSession().setAttribute("truckLoadCount", truckLoadCount);
                request.getSession().setAttribute("cityCargoes", cargoes);
                request.getSession().setAttribute("chosenTruck", chosenTruck);
                model.addAttribute("reroute", true);

                return "logistic/addCargoes";

            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
        } else {

//        Get the trip distance and time, needed for that trip
            Integer[] trip;
            try {
                trip = directionsService.getTripTimeByTruck(chosenTruck);
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }

//        Get the list of available drivers
            List<DriverDto> drivers;
            try {
                drivers = driverHandlerService.getDriverListWithTruckBrigade(chosenTruck, trip[1]);
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }

//        Adding attributes
            model.addAttribute("distance", trip[0]);
            model.addAttribute("duration", trip[1]);
            model.addAttribute("reroute", true);
            request.getSession().setAttribute("brigade", chosenTruck.getBrigadeStr());
            request.getSession().setAttribute("drivers", drivers);
            request.getSession().setAttribute("chosenTruck", chosenTruck);

            return "logistic/addDrivers";
        }
    }
}

package net.medrag.controller.warehouse;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.RouteService;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.WaypointService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller for servicing waypoints.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-wp")
public class WarehouseController {

    private CityService<CityDto, City> cityService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

    private RouteService routeService;

    @Autowired
    public void setRouteService(RouteService routeService) {
        this.routeService = routeService;
    }

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    /**
     * Method gets from database and adds to session actual waypoints in chosen city
     *
     * @param name - name of chosen city
     */
    @PostMapping("actual")
    public String returnView(@RequestParam String name, HttpServletRequest request)throws MedragControllerException {

        CityDto city = null;
        try {
            city = cityService.getDtoByNaturalId(new CityDto(), new City(), name);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (city == null){
        return "redirect: ../whm-main";
        }

        List<WaypointDto> actualWaypoints = null;
        try {
            actualWaypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(),
                    "CITY_ID", city.getId().toString(), "COMPLETE", "false");
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        request.getSession().setAttribute("wps", actualWaypoints);
        request.getSession().setAttribute("warehouseOfCity", name);

        return "warehouse/warehouse";
    }

    @GetMapping("complete/{index}")
    public String completeWaypoint(@PathVariable Integer index, HttpServletRequest request)throws MedragControllerException{

        List<WaypointDto> waypoints = (List<WaypointDto>) request.getSession().getAttribute("wps");
        WaypointDto completedWP = waypoints.remove(index.intValue());

        try {
            routeService.completeWaypoint(completedWP);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "warehouse/warehouse";
    }

}

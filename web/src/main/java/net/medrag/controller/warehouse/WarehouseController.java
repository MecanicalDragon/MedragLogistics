package net.medrag.controller.warehouse;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.domain.dto.CityDto;
import net.medrag.model.domain.dto.WaypointDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.RouteService;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.WaypointService;
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
     * @param city - name of chosen city
     */
    @GetMapping()
    public String returnView(@RequestParam String city, HttpServletRequest request) throws MedragControllerException {

        CityDto cityDto;
        try {
            cityDto = cityService.getDtoByNaturalId(new CityDto(), new City(), city);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (cityDto == null) {
            return "redirect: ../whm-main";
        }

        List<WaypointDto> actualWaypoints;
        try {
            actualWaypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(),
                    "CITY_ID", cityDto.getId().toString(), "COMPLETE", "false");
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        request.getSession().setAttribute("wps", actualWaypoints);
        request.getSession().setAttribute("warehouseOfCity", cityDto.getName());

        return "warehouse/warehouse";
    }

    @PostMapping()
    public String completeWaypoint(@RequestParam Integer index, HttpServletRequest request) throws MedragControllerException {

        List<WaypointDto> waypoints = (List<WaypointDto>) request.getSession().getAttribute("wps");
        WaypointDto completedWP = waypoints.get(index);

        try {
            routeService.completeWaypoint(completedWP);
            waypoints.remove(index.intValue());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "warehouse/warehouse";
    }

}

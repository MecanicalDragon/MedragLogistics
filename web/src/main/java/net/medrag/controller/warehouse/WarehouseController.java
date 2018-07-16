package net.medrag.controller.warehouse;

import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.WaypointHandlerService;
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

    private WaypointHandlerService waypointHandlerService;

    @Autowired
    public void setWaypointHandlerService(WaypointHandlerService waypointHandlerService) {
        this.waypointHandlerService = waypointHandlerService;
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
    public String returnView(@RequestParam String name, HttpServletRequest request){

        CityDto city = cityService.getDtoByNaturalId(new CityDto(), new City(), name);

        if (city == null){
        return "redirect: ../whm-main";
        }

        List<WaypointDto> actualWaypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(),
                "CITY_ID", city.getId().toString(), "COMPLETE", "false");

        request.getSession().setAttribute("wps", actualWaypoints);
        request.getSession().setAttribute("warehouseOfCity", name);

        return "warehouse/warehouse";
    }

    @GetMapping("complete/{index}")
    public String completeWaypoint(@PathVariable Integer index, HttpServletRequest request){

        List<WaypointDto> waypoints = (List<WaypointDto>) request.getSession().getAttribute("wps");
        WaypointDto completedWP = waypoints.remove(index.intValue());

        waypointHandlerService.completeWaypoint(completedWP);


        return "warehouse/warehouse";
    }
}

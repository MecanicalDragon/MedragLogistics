package net.medrag.controller.warehouse;

import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Waypoint;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.WaypointDto;
import net.medrag.model.service.dto.CityService;
import net.medrag.model.service.dto.WaypointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-wp")
public class WaypointController {

    private CityService<CityDto, City> cityService;

    private WaypointService<WaypointDto, Waypoint> waypointService;

    @Autowired
    public void setWaypointService(WaypointService<WaypointDto, Waypoint> waypointService) {
        this.waypointService = waypointService;
    }

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @PostMapping()
    public String returnView(@RequestParam String name){

        CityDto city = cityService.getDtoByNaturalId(new CityDto(), new City(), name);

        if (city == null){
        return "redirect: whm-main";
        }

        List<WaypointDto> actualWaypoints = waypointService.getDtoList(new WaypointDto(), new Waypoint(),
                "CITY_ID", city.getId().toString(), "COMPLETE", "false");

        System.out.println("");
        System.out.println("");
        System.out.println(actualWaypoints);
        System.out.println("");
        System.out.println("");

        return "redirect: whm-main";
    }
}

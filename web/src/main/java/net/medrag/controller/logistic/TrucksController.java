package net.medrag.controller.logistic;

import net.medrag.domain.entity.Truck;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.TruckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles trucks.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-route")
public class TrucksController {

    private TruckService<TruckDto, Truck> truckService;

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    /**
     * Getting the trucks managing page
     *
     * @param request - request
     * @return - trucks.jsp
     * @throws MedragServiceException - throws MedragControllerException
     */
    @GetMapping
    public String getTrucks(HttpServletRequest request) throws MedragServiceException{

        request.getSession().setAttribute("chosenTruck", null);
        request.getSession().setAttribute("brigade", null);
        request.getSession().setAttribute("departureCity", null);
        request.getSession().setAttribute("cityCargoes", null);
        request.getSession().setAttribute("destinationCity", null);
        request.getSession().setAttribute("drivers", null);
        request.getSession().setAttribute("truckLoad", null);
        request.getSession().setAttribute("newTruckLoad", null);
        request.getSession().setAttribute("chosenCargoId", null);
        request.getSession().setAttribute("truckLoadCount", null);
        request.getSession().setAttribute("trucksInUse", null);

        try {
            List<TruckDto>trucks = truckService.getDtoList(new TruckDto(), new Truck(), "MANAGEABLE", "'TRUE'");
            List<TruckDto>uncompleted = truckService.getDtoList(new TruckDto(), new Truck(), "MANAGEABLE", "'NEED_TO_COMPLETE'");
            trucks.addAll(0, uncompleted);
            request.getSession().setAttribute("trucksInUse", trucks);
            return "logistic/trucks";
        } catch (MedragServiceException e) {
            throw new MedragServiceException(e);
        }
    }
}

package net.medrag.controller.logistic;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.entity.Cargo;
import net.medrag.domain.dto.CargoDto;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles main logistic page.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-main")
public class MainLogisticController {

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    /**
     * Getting list of cargoes from database ant passing it to main page. Nothing interesting.
     *
     * @param request - request
     * @return - main.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @GetMapping()
    public String returnView(HttpServletRequest request)throws MedragControllerException {

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

        List<CargoDto> cargoes;
        try {
            cargoes = cargoService.getDtoList(new CargoDto(), new Cargo(), "STATE", "'TRANSIENT'");
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        request.getSession().setAttribute("globalCargoes", cargoes);
        return "logistic/main";
    }

}

package net.medrag.controller.warehouse;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.entity.Cargo;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller, that handles warehousePage
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-main")
public class WhmainController {

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request) throws MedragControllerException {

        try {
            request.getSession().setAttribute("globalCargoes", cargoService.getDtoList(new CargoDto(), new Cargo()));
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "warehouse/main";
    }

}

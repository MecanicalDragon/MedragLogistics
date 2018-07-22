package net.medrag.controller.warehouse;

import net.medrag.model.dto.CargoDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.CargoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles warehousePage
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-main")
public class WhmainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(WhmainController.class);

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request)throws MedragServiceException{
        List<CargoDto> cargos = cargoService.getDtoList(new CargoDto(), new Cargo());
        request.getSession().setAttribute("globalCargoes", cargos);
        return "warehouse/main";
    }

}

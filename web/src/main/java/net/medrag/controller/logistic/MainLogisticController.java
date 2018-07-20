package net.medrag.controller.logistic;

import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.dto.CargoDto;
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
 * Controller, that handles main logistic page.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-main")
public class MainLogisticController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainLogisticController.class);

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    /**
     * Getting list of cargoes from database ant passing it to main page. Nothing interesting.
     */
    @GetMapping()
    public String returnView(HttpServletRequest request)throws MedragServiceException{
        List<CargoDto> cargoes = cargoService.getDtoList(new CargoDto(), new Cargo(), "STATE", "'TRANSIENT'");
        request.getSession().setAttribute("globalCargoes", cargoes);
        return "logistic/main";
    }

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {
        LOGGER.error("MedragServiceException happened: {}", ex);

        return "public/error";

    }
}

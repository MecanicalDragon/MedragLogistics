package net.medrag.controller.warehouse;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.dto.CargoDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.validator.CargoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles order.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-cargo")
public class CargoController {

    private CargoValidator cargoValidator;

//    private CargoService<CargoDto, Cargo> cargoService;
//
//    private RabbitService rabbitService;
//
//    @Autowired
//    public void setRabbitService(RabbitService rabbitService) {
//        this.rabbitService = rabbitService;
//    }
//
//    @Autowired
//    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
//        this.cargoService = cargoService;
//    }

    @Autowired
    public void setCargoValidator(CargoValidator cargoValidator) {
        this.cargoValidator = cargoValidator;
    }

    @PostMapping("addCargo")
    public String addCargo(@ModelAttribute("cargo") CargoDto newCargo, BindingResult bindingResult,
                           HttpServletRequest request, Model model) throws MedragControllerException {

        try {
            cargoValidator.validate(newCargo, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("err", true);
            return "warehouse/order";
        }

        List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("cargoList");
        if (!cargoList.contains(newCargo)) {
            cargoList.add(newCargo);
        }
        model.addAttribute("cargo", new CargoDto());
        return "warehouse/order";
    }

//    @GetMapping("changeState")
//    public String changeState(@RequestParam Integer id, @RequestParam String op) throws MedragControllerException {
//        CargoDto dtoById = null;
//        try {
//            dtoById = cargoService.getDtoById(new CargoDto(), new Cargo(), id);
//            dtoById.setState(op);
//            rabbitService.sendCargo(dtoById);
//        } catch (MedragServiceException e) {
//            throw new MedragControllerException(e);
//        }
//        return "redirect: ../whm-main";
//    }
}



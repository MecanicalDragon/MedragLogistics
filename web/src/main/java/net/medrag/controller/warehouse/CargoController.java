package net.medrag.controller.warehouse;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.form.CargoForm;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.service.CargoService;
import net.medrag.model.validator.CargoValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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

    @Autowired
    public void setCargoValidator(CargoValidator cargoValidator) {
        this.cargoValidator = cargoValidator;
    }

    @PostMapping("addCargo")
    public String addCargo(@ModelAttribute("cargo") CargoForm newCargo, BindingResult bindingResult,
                           HttpServletRequest request, Model model) {

        CargoDto validatedCargo = cargoValidator.validate(newCargo, bindingResult);
        if (bindingResult.hasErrors()) {
            return "warehouse/order";
        }

        List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("cargoList");
        cargoList.add(validatedCargo);
        request.getSession().setAttribute("cargoList", cargoList);
        model.addAttribute("cargo", new CargoDto());
        return "warehouse/order";
    }

}

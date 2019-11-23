package net.medrag.controller.warehouse;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.CargoDto;
import net.medrag.service.MedragServiceException;
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

    @Autowired
    public void setCargoValidator(CargoValidator cargoValidator) {
        this.cargoValidator = cargoValidator;
    }

    /**
     * Adding new cargo method
     *
     * @param newCargo - new cargo
     * @param bindingResult - errors in cargo fields
     * @param request - request
     * @param model - model
     * @return - the same page
     * @throws MedragControllerException - throws MedragControllerException
     */
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

}



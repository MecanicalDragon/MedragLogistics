package net.medrag.controller.warehouse;

import net.medrag.dto.CargoDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.service.dto.CargoService;
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

    private CargoService<CargoDto, Cargo> cargoService;

    @Autowired
    public void setCargoService(CargoService<CargoDto, Cargo> cargoService) {
        this.cargoService = cargoService;
    }

    @Autowired
    public void setCargoValidator(CargoValidator cargoValidator) {
        this.cargoValidator = cargoValidator;
    }

    @PostMapping("addCargo")
    public String addCargo(@ModelAttribute("cargo") CargoDto newCargo, BindingResult bindingResult,
                           HttpServletRequest request, Model model) {

        cargoValidator.validate(newCargo, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("err", true);
            return "warehouse/order";
        }

        List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("cargoList");
        cargoList.add(newCargo);
        request.getSession().setAttribute("cargoList", cargoList);
        model.addAttribute("cargo", new CargoDto());
        return "warehouse/order";
    }

    @GetMapping("changeState")
    public String deliver(@RequestParam Integer id, @RequestParam Integer op, HttpServletRequest request) {
        List<CargoDto> cargos = (List<CargoDto>) request.getSession().getAttribute("globalCargoes");
        CargoDto deliveredCargo = null;
        for (CargoDto cargo : cargos) {
            if (cargo.getId().equals(id)) {
                deliveredCargo = cargo;
                break;
            }
        }
        switch (op) {
            case 2:
                deliveredCargo.setState("ON_BOARD");
                break;
            case 3:
                deliveredCargo.setState("TRANSFER_POINT");
                break;
            case 4:
                deliveredCargo.setState("DELIVERED");
                break;
        }
        cargoService.updateDtoStatus(deliveredCargo, new Cargo());

        return "redirect: ../whm-main";
    }

}

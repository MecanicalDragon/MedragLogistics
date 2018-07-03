package net.medrag.controller.warehouse;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.model.domain.entity.Cargo;
import net.medrag.model.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @PostMapping("addCargo")
    public String addCargo(@ModelAttribute("cargo") CargoDto newCargo,
                           HttpServletRequest request, Model model) {

        CustomerDto customer = (CustomerDto)request.getSession().getAttribute("owner");
        List<CargoDto> cargoList = (List<CargoDto>)request.getSession().getAttribute("cargoList");
        cargoList.add(newCargo);
        request.getSession().setAttribute("cargoList", cargoList);

        model.addAttribute("owner", customer);
        model.addAttribute("cargoList", cargoList);
        model.addAttribute("cargo", new CargoDto());
        return "warehouse/order";
    }

}

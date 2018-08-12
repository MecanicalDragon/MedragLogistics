package net.medrag.controller.warehouse;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.dto.CargoDto;
import net.medrag.model.domain.dto.CustomerDto;
import net.medrag.model.domain.dto.OrderrDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.OrderHandlingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-order")
public class OrderController {

    private OrderHandlingService orderHandlingService;

    @Autowired
    public void setOrderHandlingService(OrderHandlingService orderHandlingService) {
        this.orderHandlingService = orderHandlingService;
    }

    @GetMapping("compile")
    public String compileOrder(HttpServletRequest request, Model model)throws MedragControllerException {

        if (request.getSession().getAttribute("cargoList") != null) {

            List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("cargoList");
            CustomerDto owner = (CustomerDto) request.getSession().getAttribute("owner");

            OrderrDto order = null;
            try {
                order = orderHandlingService.compileOrder(cargoList, owner);
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
            request.getSession().setAttribute("cargoList", null);
            model.addAttribute("order", order);
        }

        return "warehouse/compiledOrder";
    }

    @GetMapping("deliver/{index}")
    public String deliver(@PathVariable Integer index, HttpServletRequest request) throws MedragControllerException{
        List<CargoDto> cargoes = (List<CargoDto>) request.getSession().getAttribute("globalCargoes");
        CargoDto deliveredCargo = cargoes.get(index);
        try {
            orderHandlingService.deliverCargo(deliveredCargo);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        return "redirect: ../../whm-main";
    }

}

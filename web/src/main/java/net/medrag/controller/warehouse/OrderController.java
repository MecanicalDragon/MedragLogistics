package net.medrag.controller.warehouse;

import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CustomerDto;
import net.medrag.model.dto.OrderrDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.OrderCompilingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
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

    private OrderCompilingService orderCompilingService;

    @Autowired
    public void setOrderCompilingService(OrderCompilingService orderCompilingService) {
        this.orderCompilingService = orderCompilingService;
    }

    @GetMapping("compile")
    public String compileOrder(HttpServletRequest request, Model model)throws MedragServiceException {

        if (request.getSession().getAttribute("cargoList") != null) {

            List<CargoDto> cargoList = (List<CargoDto>) request.getSession().getAttribute("cargoList");
            CustomerDto owner = (CustomerDto) request.getSession().getAttribute("owner");

            OrderrDto order = orderCompilingService.compileOrder(cargoList, owner);
            request.getSession().setAttribute("cargoList", null);
            model.addAttribute("order", order);
        }

        return "warehouse/compiledOrder";
    }

    @GetMapping("deliver/{index}")
    public String deliver(@PathVariable Integer index, HttpServletRequest request) throws MedragServiceException{
        List<CargoDto> cargoes = (List<CargoDto>) request.getSession().getAttribute("globalCargoes");
        CargoDto deliveredCargo = cargoes.get(index);
        orderCompilingService.deliverCargo(deliveredCargo);
        return "redirect: ../../whm-main";
    }

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {

        return "public/error";

    }
}

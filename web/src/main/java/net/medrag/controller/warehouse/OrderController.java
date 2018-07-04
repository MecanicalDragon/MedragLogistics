package net.medrag.controller.warehouse;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.dto.OrderDto;
import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.service.OrderCompilingService;
import net.medrag.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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
    public String compileOrder(HttpServletRequest request, Model model) {

        List<CargoDto> cargoList = (List<CargoDto>)request.getSession().getAttribute("cargoList");
        CustomerDto owner = (CustomerDto)request.getSession().getAttribute("owner");

        OrderDto order = orderCompilingService.compileOrder(cargoList, owner);

        model.addAttribute("order", order);
        return "warehouse/compiledOrderPage";
    }
}

package net.medrag.controller.manager;

import net.medrag.dto.OrderDto;
import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Controller, that handles mangerPage
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("mgr-main")
public class ManagerController {

    private OrderService<OrderDto, Orderr> orderService;

    @Autowired
    public void setOrderService(OrderService<OrderDto, Orderr> orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public String returnView(Model model){
        List<OrderDto> orders = orderService.getDtoList(new OrderDto(), new Orderr());
        model.addAttribute("orders", orders);
        model.addAttribute("ordersCount", orders.size());
        return "manager/mainPage";
    }
}

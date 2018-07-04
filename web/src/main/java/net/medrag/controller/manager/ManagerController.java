package net.medrag.controller.manager;

import net.medrag.dto.OrderrDto;
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

    private OrderService<OrderrDto, Orderr> orderService;

    @Autowired
    public void setOrderService(OrderService<OrderrDto, Orderr> orderService) {
        this.orderService = orderService;
    }

    @GetMapping()
    public String returnView(Model model){
        List<OrderrDto> orders = orderService.getDtoList(new OrderrDto(), new Orderr());
        System.out.println(orders);
        model.addAttribute("orders", orders);
        model.addAttribute("ordersCount", orders.size());
        return "manager/managerPage";
    }
}

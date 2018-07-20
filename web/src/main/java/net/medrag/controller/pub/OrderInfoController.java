package net.medrag.controller.pub;

import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.dto.OrderrDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("orderInfo")
public class OrderInfoController {

    private OrderService<OrderrDto, Orderr> orderService;

    @Autowired
    public void setOrderService(OrderService<OrderrDto, Orderr> orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{index}")
    public String getInfo(@PathVariable String index, Model model) throws MedragServiceException{

        OrderrDto order = orderService.getDtoByNaturalId(new OrderrDto(), new Orderr(), index);
        model.addAttribute("order", order);
        return "public/info";
    }

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {

        return "public/error";

    }
}

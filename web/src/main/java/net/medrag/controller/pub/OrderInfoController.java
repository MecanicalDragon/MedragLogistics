package net.medrag.controller.pub;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.OrderrDto;
import net.medrag.model.service.DirectionsService;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("orderInfo")
public class OrderInfoController {

    private OrderService<OrderrDto, Orderr> orderService;

    private DirectionsService directionsService;

    @Autowired
    public void setDirectionsService(DirectionsService directionsService) {
        this.directionsService = directionsService;
    }

    @Autowired
    public void setOrderService(OrderService<OrderrDto, Orderr> orderService) {
        this.orderService = orderService;
    }

    @GetMapping("{index}")
    public String getInfo(@PathVariable String index, Model model) throws MedragControllerException {

        OrderrDto order = null;
        try {
            order = orderService.getDtoByNaturalId(new OrderrDto(), new Orderr(), index);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        if (order == null){
            model.addAttribute("msg", "No such order in our database.");
            return "public/error";
        }

        List<Integer> completeness = new ArrayList<>();
        for (CargoDto cargo : order.getCargoes()){
            try {
                completeness.add(directionsService.getComletePersent(cargo));
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
        }

        model.addAttribute("completeness", completeness);
        model.addAttribute("order", order);
        return "public/info";
    }

}

package net.medrag.controller.resource;

import net.medrag.dto.TruckDto;
import net.medrag.form.TruckForm;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.service.TruckService;
import net.medrag.validator.TruckValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles trucks.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("rsm-truck")
public class TruckController {

    private TruckService<TruckDto, Truck> truckService;

    private TruckValidator truckValidator;

    @Autowired
    public void setTruckService(TruckService<TruckDto, Truck> truckService) {
        this.truckService = truckService;
    }

    @Autowired
    public void setTruckValidator(TruckValidator truckValidator) {
        this.truckValidator = truckValidator;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request, Model model){
        List<TruckDto> trucks = truckService.getDtoList(new TruckDto(), new Truck());
        request.getSession().setAttribute("truckList", trucks);
        model.addAttribute("truck", new TruckForm());
        return "resource/trucks";
    }

    @PostMapping("addTruck")
    public String addTruck(@ModelAttribute("truck") TruckForm truck, BindingResult bindingResult, Model model){

        TruckDto validatedTruck = truckValidator.validate(truck, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("err", true);
            return "resource/trucks";
        }
        truckService.addDto(validatedTruck, new Truck());
        return "redirect: ../rsm-truck";
    }

}

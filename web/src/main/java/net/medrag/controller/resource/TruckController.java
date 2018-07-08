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
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("editingTruck", new TruckForm());
        return "resource/trucks";
    }

    @PostMapping("editTruck")
    public String editTruck(@ModelAttribute("editingTruck") TruckForm truck, BindingResult bindingResult, Model model){

        TruckDto validatedTruck = truckValidator.validateEdits(truck, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("editErr", true);
            model.addAttribute("truck", new TruckForm());
            model.addAttribute("editingTruck", truck);
            return "resource/trucks";
        }

        truckService.updateDtoStatus(validatedTruck, new Truck());

        return "redirect: ../rsm-truck";
    }

    @PostMapping("addTruck")
    public String addTruck(@ModelAttribute("truck") TruckForm truck, BindingResult bindingResult, Model model){

        TruckDto validatedTruck = truckValidator.validate(truck, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("err", true);
            model.addAttribute("truck", truck);
            model.addAttribute("editingTruck", new TruckForm());
            return "resource/trucks";
        }
        Integer i = truckService.addDto(validatedTruck, new Truck());
        return "redirect: ../rsm-truck";
    }

    @GetMapping("remove/{id}")
    public String removeTruck(@PathVariable Integer id, Model model){
        TruckDto removingTruck = new TruckDto();
        removingTruck.setId(id);
        truckService.removeDto(removingTruck, new Truck());
        return "redirect: ../../rsm-truck";
    }

    @GetMapping("changeState")
    public String changeState(@RequestParam Integer id, @RequestParam Integer op, HttpServletRequest request) {
        List<TruckDto> truckList = (List<TruckDto>) request.getSession().getAttribute("truckList");
        TruckDto repairingTruck = null;
        for (TruckDto truck : truckList) {
            if (truck.getId().equals(id)) {
                repairingTruck = truck;
                break;
            }
        }

        switch (op) {
            case 0:
                repairingTruck.setStatus("IN_USE");
                break;
            case 1:
                repairingTruck.setStatus("STAY_IDLE");
                break;
            case 2:
                repairingTruck.setStatus("IN_SERVICE");
                break;
        }
        truckService.updateDtoStatus(repairingTruck, new Truck());

        return "redirect: ../rsm-truck";
    }

}

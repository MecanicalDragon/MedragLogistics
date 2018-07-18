package net.medrag.controller.resource;

import net.medrag.model.dto.TruckDto;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.TruckService;
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
    public String returnView(HttpServletRequest request, Model model)throws MedragServiceException{
        List<TruckDto> trucks = truckService.getDtoList(new TruckDto(), new Truck());
        request.getSession().setAttribute("truckList", trucks);
        model.addAttribute("truck", new TruckDto());
        model.addAttribute("editableTruck", new TruckDto());
        return "resource/trucks";
    }

    @PostMapping("editTruck")
    public String editTruck(@ModelAttribute("editableTruck") TruckDto truck, BindingResult bindingResult, Model model)throws MedragServiceException{

        TruckDto validatedTruck = truckValidator.validateEdits(truck, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("editErr", true);
            model.addAttribute("truck", new TruckDto());
            model.addAttribute("editableTruck", truck);
            return "resource/trucks";
        }

        truckService.updateDtoStatus(validatedTruck, new Truck());

        return "redirect: ../rsm-truck";
    }

    @PostMapping("addTruck")
    public String addTruck(@ModelAttribute("truck") TruckDto truck, BindingResult bindingResult, Model model)throws MedragServiceException{

        truckValidator.validate(truck, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("err", true);
            model.addAttribute("truck", truck);
            model.addAttribute("editableTruck", new TruckDto());
            return "resource/trucks";
        }

        truckService.addDto(truck, new Truck());
        return "redirect: ../rsm-truck";
    }

    @GetMapping("remove/{id}")
    public String removeTruck(@PathVariable Integer id, Model model)throws MedragServiceException{
        TruckDto deletableTruck = new TruckDto();
        deletableTruck.setId(id);
        truckService.removeDto(deletableTruck, new Truck());
        return "redirect: ../../rsm-truck";
    }

    @GetMapping("changeState")
    public String changeState(@RequestParam Integer id, @RequestParam Integer op, HttpServletRequest request)throws MedragServiceException {
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

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {

        return "public/error";

    }

}

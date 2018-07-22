package net.medrag.controller.resource;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.dto.TruckDto;
import net.medrag.model.domain.entity.Truck;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.TruckService;
import net.medrag.validator.TruckValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    public String returnView(HttpServletRequest request, Model model)throws MedragControllerException {
        List<TruckDto> trucks = null;
        try {
            trucks = truckService.getDtoList(new TruckDto(), new Truck());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        request.getSession().setAttribute("truckList", trucks);
        model.addAttribute("truck", new TruckDto());
        model.addAttribute("editableTruck", new TruckDto());
        return "resource/trucks";
    }

    @PostMapping("editTruck")
    public String editTruck(@ModelAttribute("editableTruck") TruckDto truck, BindingResult bindingResult, Model model)throws MedragControllerException{

        TruckDto validatedTruck = null;
        try {
            validatedTruck = truckValidator.validateEdits(truck, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("editErr", true);
            model.addAttribute("truck", new TruckDto());
            model.addAttribute("editableTruck", truck);
            return "resource/trucks";
        }

        try {
            truckService.updateDtoStatus(validatedTruck, new Truck());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-truck";
    }

    @PostMapping("addTruck")
    public String addTruck(@ModelAttribute("truck") TruckDto truck, BindingResult bindingResult, Model model)throws MedragControllerException{

        try {
            truckValidator.validate(truck, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("err", true);
            model.addAttribute("truck", truck);
            model.addAttribute("editableTruck", new TruckDto());
            return "resource/trucks";
        }

        try {
            truckService.addDto(truck, new Truck());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        return "redirect: ../rsm-truck";
    }

    @GetMapping("remove/{id}")
    public String removeTruck(@PathVariable Integer id, Model model)throws MedragControllerException{
        TruckDto deletableTruck = new TruckDto();
        deletableTruck.setId(id);
        try {
            truckService.removeDto(deletableTruck, new Truck());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        return "redirect: ../../rsm-truck";
    }

    @GetMapping("changeState")
    public String changeState(@RequestParam Integer id, @RequestParam Integer op, HttpServletRequest request)throws MedragControllerException {
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
        try {
            truckService.updateDtoStatus(repairingTruck, new Truck());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-truck";
    }

}

package net.medrag.controller.resource;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.enums.TruckStatus;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.TruckService;
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

    /**
     * Getting the trucks page
     *
     * @param request - request
     * @param model   - model
     * @return - trucks.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @GetMapping()
    public String returnView(HttpServletRequest request, Model model) throws MedragControllerException {
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

    /**
     * Edit chosen truck post method
     *
     * @param truck        - edited truck
     * @param bindingResult - errors in edits
     * @param model         - model
     * @return - trucks.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("editTruck")
    public String editTruck(@ModelAttribute("editableTruck") TruckDto truck, BindingResult bindingResult, Model model) throws MedragControllerException {

        TruckDto validatedTruck = null;
        try {
            validatedTruck = truckValidator.validateEdits(truck, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()) {
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

    /**
     * Adding new truck post method
     *
     * @param truck        - new driver
     * @param bindingResult - errors in edits
     * @param model         - model
     * @return - trucks.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("addTruck")
    public String addTruck(@ModelAttribute("truck") TruckDto truck, BindingResult bindingResult, Model model) throws MedragControllerException {

        try {
            truckValidator.validate(truck, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()) {
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

    /**
     * Removing chosen truck post method
     *
     * @param index - index of chosen truck in session trucks list
     * @param request - request
     * @return - trucks.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("remove")
    public String removeTruck(@RequestParam Integer index, HttpServletRequest request) throws MedragControllerException {

        List<TruckDto>trucks = (List<TruckDto>)request.getSession().getAttribute("truckList");
        TruckDto deletableTruck = trucks.get(index);
        try {
            truckService.removeDto(deletableTruck, new Truck());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        return "redirect: ../rsm-truck";
    }

    /**
     * Changing truck status method
     *
     * @param index   - index of chosen truck in session trucks list
     * @param state   - new status
     * @param request - request
     * @return - trucks.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("changeState")
    public String changeState(@RequestParam Integer index, @RequestParam String state, HttpServletRequest request) throws MedragControllerException {
        List<TruckDto> truckList = (List<TruckDto>) request.getSession().getAttribute("truckList");
        TruckDto truck = truckList.get(index);
        truck.setStatus(TruckStatus.valueOf(state));

        try {
            truckService.updateDtoStatus(truck, new Truck());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-truck";
    }

}

package net.medrag.controller.resource;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.service.DriverHandlerService;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.DriverService;
import net.medrag.validator.DriverValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles drivers.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("rsm-driver")
public class DriverController {

    private DriverService<DriverDto, Driver> driverService;

    private DriverValidator driverValidator;

    private DriverHandlerService driverHandlerService;

    @Autowired
    public void setDriverHandlerService(DriverHandlerService driverHandlerService) {
        this.driverHandlerService = driverHandlerService;
    }

    @Autowired
    public void setDriverService(DriverService<DriverDto, Driver> driverService) {
        this.driverService = driverService;
    }

    @Autowired
    public void setDriverValidator(DriverValidator driverValidator) {
        this.driverValidator = driverValidator;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request, Model model) throws MedragControllerException {
        List<DriverDto> drivers = null;
        try {
            drivers = driverService.getDtoList(new DriverDto(), new Driver());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        request.getSession().setAttribute("driverList", drivers);
        model.addAttribute("driver", new DriverDto());
        model.addAttribute("editableDriver", new DriverDto());
        return "resource/drivers";
    }

    @PostMapping("addDriver")
    public String addDriver(@ModelAttribute("driver") DriverDto driver, BindingResult bindingResult, Model model) throws MedragControllerException{

        try {
            driverValidator.validate(driver, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("err", true);
            model.addAttribute("driver", driver);
            model.addAttribute("editableDriver", new DriverDto());
            return "resource/drivers";
        }

        try {
            driverHandlerService.identifyNewDriver(driver);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-driver";
    }

    @PostMapping("editDriver")
    public String editDriver(@ModelAttribute("editableDriver") DriverDto driver, BindingResult bindingResult, Model model)throws MedragControllerException{

        DriverDto validatedDriver = null;
        try {
            validatedDriver = driverValidator.validateEdits(driver, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()){
            model.addAttribute("editErr", true);
            model.addAttribute("driver", new DriverDto());
            model.addAttribute("editableDriver", driver);
            return "resource/drivers";
        }

        if (!validatedDriver.getEmail().equalsIgnoreCase(driver.getEmail())){
            try {
                driverHandlerService.updateDriver(validatedDriver);
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
        } else {
            try {
                driverService.updateDtoStatus(validatedDriver, new Driver());
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }
        }

        return "redirect: ../rsm-driver";
    }

    @GetMapping("remove/{id}")
    public String removeDriver(@PathVariable Integer id, Model model)throws MedragServiceException{
        DriverDto removableDriver = driverService.getDtoById(new DriverDto(), new Driver(), id);
        driverHandlerService.removeDriver(removableDriver);
        return "redirect: ../../rsm-driver";
    }

    @GetMapping("changeState")
    public String changeState(@RequestParam Integer id, @RequestParam Integer op, HttpServletRequest request) throws MedragControllerException{
        List<DriverDto> driverList = (List<DriverDto>) request.getSession().getAttribute("driverList");
        DriverDto changingDriver = new DriverDto();
        for (DriverDto driver : driverList) {
            if (driver.getId().equals(id)) {
                changingDriver = driver;
                break;
            }
        }

        switch (op) {
            case 0:
                changingDriver.setState("REST");
                break;
            case 1:
                changingDriver.setState("ON_SHIFT");
                break;
            case 2:
                changingDriver.setState("DRIVING");
                break;
            case 3:
                changingDriver.setState("PORTER");
                break;
            case 4:
                changingDriver.setState("READY_TO_ROUTE");
                break;
            default: changingDriver.setState("REST");
        }

        try {
            driverService.updateDtoStatus(changingDriver, new Driver());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-driver";
    }

}

package net.medrag.controller.resource;

import net.medrag.model.dto.DriverDto;
import net.medrag.model.domain.entity.Driver;
import net.medrag.model.service.DriverIdentifierService;
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

    private DriverIdentifierService driverIdentifierService;

    @Autowired
    public void setDriverIdentifierService(DriverIdentifierService driverIdentifierService) {
        this.driverIdentifierService = driverIdentifierService;
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
    public String returnView(HttpServletRequest request, Model model) {
        List<DriverDto> drivers = driverService.getDtoList(new DriverDto(), new Driver());
        request.getSession().setAttribute("driverList", drivers);
        model.addAttribute("driver", new DriverDto());
        model.addAttribute("editableDriver", new DriverDto());
        return "resource/drivers";
    }

    @PostMapping("addDriver")
    public String addDriver(@ModelAttribute("driver") DriverDto driver, BindingResult bindingResult, Model model) {

        driverValidator.validate(driver, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute("err", true);
            model.addAttribute("driver", driver);
            model.addAttribute("editableDriver", new DriverDto());
            return "resource/drivers";
        }

        driverIdentifierService.identifyNewDriver(driver);

        return "redirect: ../rsm-driver";
    }

    @PostMapping("editDriver")
    public String editDriver(@ModelAttribute("editableDriver") DriverDto driver, BindingResult bindingResult, Model model){

        DriverDto validatedDriver = driverValidator.validateEdits(driver, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("editErr", true);
            model.addAttribute("driver", new DriverDto());
            model.addAttribute("editableDriver", driver);
            return "resource/drivers";
        }

        if (!validatedDriver.getEmail().equalsIgnoreCase(driver.getEmail())){
            driverIdentifierService.updateDriver(validatedDriver);
        } else {
            driverService.updateDtoStatus(validatedDriver, new Driver());
        }

        return "redirect: ../rsm-driver";
    }

    @GetMapping("remove/{id}")
    public String removeDriver(@PathVariable Integer id, Model model){
        DriverDto removableDriver = driverService.getDtoById(new DriverDto(), new Driver(), id);
        driverIdentifierService.removeDriver(removableDriver);
        return "redirect: ../../rsm-driver";
    }

    @GetMapping("changeState")
    public String changeState(@RequestParam Integer id, @RequestParam Integer op, HttpServletRequest request) {
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

        driverService.updateDtoStatus(changingDriver, new Driver());

        return "redirect: ../rsm-driver";
    }

}

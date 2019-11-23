package net.medrag.controller.resource;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.DriverDto;
import net.medrag.domain.entity.Driver;
import net.medrag.domain.enums.DriverState;
import net.medrag.service.api.DriverHandlerService;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.DriverService;
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

    /**
     * Getting the drivers page
     *
     * @param request - request
     * @param model   - model
     * @return - drivers.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
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

    /**
     * Adding new city post method
     *
     * @param driver        - new driver
     * @param bindingResult - errors in edits
     * @param model         - model
     * @return - drivers.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("addDriver")
    public String addDriver(@ModelAttribute("driver") DriverDto driver, BindingResult bindingResult, Model model) throws MedragControllerException {

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

    /**
     * Edit chosen driver post method
     *
     * @param driver        - edited driver
     * @param bindingResult - errors in edits
     * @param model         - model
     * @return - drivers.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("editDriver")
    public String editDriver(@ModelAttribute("editableDriver") DriverDto driver, BindingResult bindingResult, Model model) throws MedragControllerException {

        DriverDto validatedDriver = null;
        try {
            validatedDriver = driverValidator.validateEdits(driver, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("editErr", true);
            model.addAttribute("driver", new DriverDto());
            model.addAttribute("editableDriver", driver);
            return "resource/drivers";
        }

        if (!validatedDriver.getEmail().equalsIgnoreCase(driver.getEmail())) {
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

    /**
     * Removing chosen driver post method
     *
     * @param index   - index of chosen driver in session drivers list
     * @param request - request
     * @return - drivers.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("remove")
    public String removeDriver(@RequestParam Integer index, HttpServletRequest request) throws MedragControllerException {
        List<DriverDto> drivers = (List<DriverDto>) request.getSession().getAttribute("driverList");
        DriverDto removableDriver = drivers.get(index);
        try {
            driverService.removeDto(removableDriver, new Driver());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        return "redirect: ../rsm-driver";
    }

    /**
     * Changing driver state method
     *
     * @param index   - index of chosen driver in session drivers list
     * @param state   - new state
     * @param request - request
     * @return - drivers.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("changeState")
    public String changeState(@RequestParam Integer index, @RequestParam String state, HttpServletRequest request) throws MedragControllerException {
        List<DriverDto> driverList = (List<DriverDto>) request.getSession().getAttribute("driverList");
        DriverDto changingDriver = driverList.get(index);
        changingDriver.setState(DriverState.valueOf(state));

        try {
            driverService.updateDtoStatus(changingDriver, new Driver());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-driver";
    }

}

package net.medrag.controller.resource;


import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.entity.City;
import net.medrag.service.api.CityHandlingService;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.CityService;
import net.medrag.validator.CityValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles city.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("rsm-city")
public class CityController {

    private CityService<CityDto, City> cityService;

    private CityValidator cityValidator;

    private CityHandlingService cityHandlingService;

    @Autowired
    public void setCityHandlingService(CityHandlingService cityHandlingService) {
        this.cityHandlingService = cityHandlingService;
    }

    @Autowired
    public void setCityValidator(CityValidator cityValidator) {
        this.cityValidator = cityValidator;
    }

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request, Model model) throws MedragControllerException {
        List<CityDto> cities = null;
        try {
            cities = cityService.getDtoList(new CityDto(), new City());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        request.getSession().setAttribute("cities", cities);
        model.addAttribute("city", new CityDto());
        model.addAttribute("editingCity", new CityDto());
        return "resource/cities";
    }

    @PostMapping("editCity")
    public String editCity(@ModelAttribute("editingCity") CityDto city, BindingResult bindingResult, Model model) throws MedragControllerException {

        CityDto validatedCity = null;
        try {
            validatedCity = cityValidator.validateEdits(city, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("editErr", true);
            model.addAttribute("city", new CityDto());
            model.addAttribute("editingCity", city);
            return "resource/cities";
        }

        try {
            cityService.updateDtoStatus(validatedCity, new City());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-city";
    }

    @PostMapping("addCity")
    public String addCity(@ModelAttribute("city") CityDto city, BindingResult bindingResult, Model model) throws MedragControllerException {

        try {
            cityValidator.validate(city, bindingResult);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("err", true);
            model.addAttribute("city", city);
            model.addAttribute("editingCity", new CityDto());
            return "resource/cities";
        }
        try {
            cityService.addDto(city, new City());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        return "redirect: ../rsm-city";
    }

    @PostMapping("remove")
    public String removeCity(@RequestParam Integer index, HttpServletRequest request, Model model) throws MedragControllerException {
        List<CityDto> cities = (List<CityDto>) request.getSession().getAttribute("cities");
        CityDto removingCity = cities.get(index);
        try {
            if (cityHandlingService.removeCity(removingCity)) {
                cityService.removeDto(removingCity, new City());
                return "redirect: ../rsm-city";
            } else {
                model.addAttribute("active", true);
                model.addAttribute("city", new CityDto());
                model.addAttribute("editingCity", new CityDto());
                return "resource/cities";
            }
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
    }

}

package net.medrag.controller.resource;


import net.medrag.dto.CityDto;
import net.medrag.model.domain.entity.City;
import net.medrag.model.service.CityService;
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

    @Autowired
    public void setCityValidator(CityValidator cityValidator) {
        this.cityValidator = cityValidator;
    }

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request, Model model){
        List<CityDto> cities = cityService.getDtoList(new CityDto(), new City());
        request.getSession().setAttribute("cities", cities);
        model.addAttribute("city", new CityDto());
        model.addAttribute("editingCity", new CityDto());
        return "resource/cities";
    }

    @PostMapping("editCity")
    public String editCity(@ModelAttribute("editingCity") CityDto city, BindingResult bindingResult, Model model){

        CityDto validatedCity = cityValidator.validateEdits(city, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("editErr", true);
            model.addAttribute("city", new CityDto());
            model.addAttribute("editingCity", validatedCity);
            return "resource/cities";
        }

        cityService.updateDtoStatus(validatedCity, new City());

        return "redirect: ../rsm-city";
    }

    @PostMapping("addCity")
    public String addCity(@ModelAttribute("city") CityDto city, BindingResult bindingResult, Model model){

        cityValidator.validate(city, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("err", true);
            model.addAttribute("city", city);
            model.addAttribute("editingCity", new CityDto());
            return "resource/cities";
        }
        cityService.addDto(city, new City());
        return "redirect: ../rsm-city";
    }

    @GetMapping("remove/{id}")
    public String removeCity(@PathVariable Integer id, Model model){
        CityDto removingCity = new CityDto();
        removingCity.setId(id);
        cityService.removeDto(removingCity, new City());
        return "redirect: ../../rsm-city";
    }

}

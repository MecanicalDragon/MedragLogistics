package net.medrag.controller.pub;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.model.domain.entity.City;
import net.medrag.model.dto.CityDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.SecurityService;
import net.medrag.model.service.dto.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Title page controller.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping({"/","title"})
public class TitleController {

    private SecurityService securityService;

    private CityService<CityDto, City> cityService;

    @Autowired
    public void setCityService(CityService<CityDto, City> cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setSecurityService(SecurityService securityService) {
        this.securityService = securityService;
    }

    @GetMapping
    public String login(Model model, String error) {
        if (error != null) {
            model.addAttribute("error", "Wrong personal id or password.");
        }
        return "public/title";
    }
    @GetMapping("identify")
    public String identify() {
        String role = securityService.getRoleOfSignedInUser();
        switch (role){
            case "ROLE_DRIVER":
                return "redirect: ../drv-main";
            case "ROLE_MANAGER":
                return "redirect: ../mgr-main";
            case "ROLE_WAREHOUSEMAN":
                return "redirect: ../whm-main";
            case "ROLE_RESOURCE":
                return "redirect: ../rsm-city";
            default:
                return "public/title";
        }
    }

    @GetMapping("map")
    public String map(Model model) throws MedragServiceException {

        List<CityDto> cities = cityService.getDtoList(new CityDto(), new City());
        model.addAttribute("cities", cities);

        return "public/map";
    }

    @GetMapping("jackson")
    public String jackson(Model model) throws MedragServiceException {

        return "public/jackson";
    }

    @PostMapping("sout")
    public String sout(@RequestParam Integer index) {


        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println(index);
        System.out.println();
        System.out.println();
        System.out.println();

        return "redirect: ../public/map";
    }

    @GetMapping("error")
    public String getError(){
        return "public/error";
    }
}

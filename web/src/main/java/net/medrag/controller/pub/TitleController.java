package net.medrag.controller.pub;

import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.dto.CityDto;
import net.medrag.model.service.SecurityService;
import net.medrag.model.service.dto.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("error")
    public String getError(){
        return "public/error";
    }
}

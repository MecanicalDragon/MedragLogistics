package net.medrag.controller.pub;

import net.medrag.service.api.SecurityService;
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

    /**
     * Getting title page
     * @param model - model
     * @param error - login error parameter
     * @return - title.jsp
     */
    @GetMapping
    public String login(Model model, String error) {
        if (error != null) {
            model.addAttribute("error", "Wrong personal id or password.");
        }
        return "public/title";
    }

    /**
     * Identifying user by his role, using spring security roles
     * @return user's main page (depends on user's role)
     */
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

    /**
     * Error page
     * @return error.jsp
     */
    @GetMapping("error")
    public String getError(){
        return "public/error";
    }

    /**
     * Access denied page
     * @return 403.jsp
     */
    @GetMapping("403")
    public String get403(){
        return "public/403";
    }
}

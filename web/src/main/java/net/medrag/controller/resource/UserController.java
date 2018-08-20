package net.medrag.controller.resource;

import net.medrag.controller.advice.MedragControllerException;
import net.medrag.domain.dto.UserDto;
import net.medrag.domain.entity.User;
import net.medrag.service.api.EmployeeIdentifierService;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.UserService;
import net.medrag.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller, that handles users.jsp
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("rsm-user")
public class UserController {

    private UserService<UserDto, User> userService;

    private EmployeeIdentifierService employeeIdentifierService;

    private UserValidator userValidator;

    @Autowired
    public void setUserValidator(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    @Autowired
    public void setEmployeeIdentifierService(EmployeeIdentifierService employeeIdentifierService) {
        this.employeeIdentifierService = employeeIdentifierService;
    }

    @Autowired
    public void setUserService(UserService<UserDto, User> userService) {
        this.userService = userService;
    }

    /**
     * Getting the users page
     *
     * @param request - request
     * @param model   - model
     * @return - users.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @GetMapping()
    public String returnView(HttpServletRequest request, Model model)throws MedragControllerException {
        List<UserDto>userList = null;
        try {
            userList = userService.getDtoList(new UserDto(), new User());
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }
        request.getSession().setAttribute("userList", userList);
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("removableUser", new UserDto());
        return "resource/users";
    }

    /**
     * Adding new user post method
     *
     * @param user        - new driver
     * @param bindingResult - errors in edits
     * @param model         - model
     * @return - users.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("addUser")
    public String addUser(@ModelAttribute("newUser") UserDto user, BindingResult bindingResult, Model model)throws MedragControllerException {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("newUser", user);
            model.addAttribute("removableUser", new UserDto());
            return "resource/users";
        }

        try {
            employeeIdentifierService.identifyEmployee(user);
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-user";
    }

    /**
     * Generate new password for user method
     *
     * @param id - user's id
     * @return - users.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("generate")
    public String generate(@RequestParam String id)throws MedragControllerException{
        try {
            employeeIdentifierService.generateNewPassword(Integer.valueOf(id));
        } catch (MedragServiceException e) {
            throw new MedragControllerException(e);
        }

        return "redirect: ../rsm-user";
    }

    /**
     * Removing chosen user post method
     *
     * @param index   - index of chosen user in session users list
     * @param request - request
     * @return - users.jsp
     * @throws MedragControllerException - throws MedragControllerException
     */
    @PostMapping("remove")
    public String remove(@RequestParam Integer index, HttpServletRequest request)throws MedragControllerException{

            try {
                List<UserDto> userList = (List<UserDto>)request.getSession().getAttribute("userList");
                UserDto user = userList.get(index);
                userService.removeDto(user, new User());
            } catch (MedragServiceException e) {
                throw new MedragControllerException(e);
            }

        return "redirect: ../rsm-user";
    }

}

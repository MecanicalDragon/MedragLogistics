package net.medrag.controller.resource;

import net.medrag.model.dto.UserDto;
import net.medrag.model.domain.entity.User;
import net.medrag.model.service.EmployeeIdentifierService;
import net.medrag.model.service.MedragServiceException;
import net.medrag.model.service.dto.UserService;
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

    @GetMapping()
    public String returnView(HttpServletRequest request, Model model)throws MedragServiceException {
        List<UserDto>userList = userService.getDtoList(new UserDto(), new User());
        request.getSession().setAttribute("userList", userList);
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("removableUser", new UserDto());
        return "resource/users";
    }

    @PostMapping("addUser")
    public String addUser(@ModelAttribute("newUser") UserDto user, BindingResult bindingResult, Model model)throws MedragServiceException {

        userValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors()){
            model.addAttribute("newUser", user);
            model.addAttribute("removableUser", new UserDto());
            return "resource/users";
        }

        employeeIdentifierService.identifyEmployee(user);

        return "redirect: ../rsm-user";
    }

    @GetMapping("generate")
    public String generate(@RequestParam String id)throws MedragServiceException{
        employeeIdentifierService.generateNewPassword(Integer.valueOf(id));

        return "redirect: ../rsm-user";
    }

    @PostMapping("remove")
    public String remove(@ModelAttribute("removableUser") UserDto user)throws MedragServiceException{

        if(user.getUsername().substring(0, 3).equalsIgnoreCase("DRV")){
            employeeIdentifierService.removeUserIfItsDriver(user);
        } else{
        userService.removeDto(user, new User());
        }

        return "redirect: ../rsm-user";
    }

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {

        return "public/error";

    }

}

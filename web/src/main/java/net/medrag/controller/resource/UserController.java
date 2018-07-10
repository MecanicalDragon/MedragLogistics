package net.medrag.controller.resource;

import net.medrag.dto.UserDto;
import net.medrag.model.domain.entity.User;
import net.medrag.model.service.EmployeeIdentifierService;
import net.medrag.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    public void setEmployeeIdentifierService(EmployeeIdentifierService employeeIdentifierService) {
        this.employeeIdentifierService = employeeIdentifierService;
    }

    @Autowired
    public void setUserService(UserService<UserDto, User> userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String returnView(HttpServletRequest request, Model model) {
        List<UserDto>userList = userService.getDtoList(new UserDto(), new User());
        request.getSession().setAttribute("userList", userList);
        model.addAttribute("newUser", new UserDto());
        model.addAttribute("removableUser", new UserDto());
        return "resource/users";
    }

    @PostMapping("addUser")
    public String addUser(@ModelAttribute("newUser") UserDto user) {

        employeeIdentifierService.identifyEmployee(user);

        return "redirect: ../rsm-driver";
    }

    @PostMapping("generate")
    public String generate(@RequestParam Integer id){

        employeeIdentifierService.generateNewPassword(id);

        return "redirect: ../rsm-user";
    }

    @PostMapping("remove")
    public String remove(@ModelAttribute("removableUser") UserDto user){

        userService.removeDto(user, new User());

        return "redirect: ../rsm-user";
    }

}

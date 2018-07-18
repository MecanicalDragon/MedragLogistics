package net.medrag.controller.warehouse;

import net.medrag.model.dto.CargoDto;
import net.medrag.model.dto.CustomerDto;
import net.medrag.model.service.MedragServiceException;
import net.medrag.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller, that handles page 'newCustomer'
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Controller
@RequestMapping("whm-newCustomer")
public class CustomerController {

    private CustomerValidator customerValidator;

    @Autowired
    public void setCustomerValidator(CustomerValidator customerValidator) {
        this.customerValidator = customerValidator;
    }

    @GetMapping()
    public String newCustomer(Model model) {
        model.addAttribute("newCustomer", new CustomerDto());
        return "warehouse/customer";
    }

    @PostMapping("clarify")
    public String addNewCustomer(@ModelAttribute("newCustomer") CustomerDto customer, BindingResult bindingResult,
                                 Model model, HttpServletRequest request) throws MedragServiceException{

        customerValidator.validate(customer, bindingResult);

        if (bindingResult.hasErrors()) {
            return "warehouse/customer";
        }

        List<CargoDto>cargoList = new ArrayList<>();
        request.getSession().setAttribute("owner", customer);
        request.getSession().setAttribute("cargoList", cargoList);
        model.addAttribute("cargo", new CargoDto());
        return "warehouse/order";
    }

    @ExceptionHandler(MedragServiceException.class)
    public String handleCustomException(MedragServiceException ex) {

        return "public/error";

    }
}

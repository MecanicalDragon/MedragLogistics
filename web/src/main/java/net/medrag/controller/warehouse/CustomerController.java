package net.medrag.controller.warehouse;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.form.CargoForm;
import net.medrag.model.domain.entity.Customer;
import net.medrag.model.service.CustomerService;
import net.medrag.model.validator.CustomerValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    private CustomerService<CustomerDto, Customer> customerService;

    private CustomerValidator customerValidator;

    @Autowired
    public void setCustomerValidator(CustomerValidator customerValidator) {
        this.customerValidator = customerValidator;
    }

    @Autowired
    public void setCustomerService(CustomerService<CustomerDto, Customer> customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public String newCustomer(Model model) {
        model.addAttribute("newCustomer", new CustomerDto());
        return "warehouse/customer";
    }

    @PostMapping("clarify")
    public String addNewCustomer(@ModelAttribute("newCustomer") CustomerDto customer, BindingResult bindingResult,
                                 Model model, HttpServletRequest request) {

        customer = customerValidator.validate(customer, bindingResult);

        if (bindingResult.hasErrors()) {
            return "warehouse/customer";
        }

        List<CargoDto>cargoList = new ArrayList<>();
        request.getSession().setAttribute("owner", customer);
        request.getSession().setAttribute("cargoList", cargoList);
        model.addAttribute("cargo", new CargoForm());
        return "warehouse/order";
    }
}

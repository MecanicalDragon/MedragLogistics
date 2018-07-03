package net.medrag.controller.warehouse;

import net.medrag.dto.CargoDto;
import net.medrag.dto.CustomerDto;
import net.medrag.model.domain.entity.Customer;
import net.medrag.model.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    public void setCustomerService(CustomerService<CustomerDto, Customer> customerService) {
        this.customerService = customerService;
    }

    @GetMapping()
    public String newCustomer(Model model) {
        model.addAttribute("newCustomer", new CustomerDto());
        return "warehouse/newCustomer";
    }

    @PostMapping("clarify")
    public String addNewCustomer(@ModelAttribute("newCustomer") CustomerDto customer, Model model, HttpServletRequest request) {
        customer = customerService.clarifyCustomer(customer);
        List<CargoDto>cargoList = new ArrayList<>();
        request.getSession().setAttribute("owner", customer);
        request.getSession().setAttribute("cargoList", cargoList);
        model.addAttribute("owner", customer);
        model.addAttribute("cargoList", cargoList);
        model.addAttribute("cargo", new CargoDto());
        return "warehouse/order";
    }
}

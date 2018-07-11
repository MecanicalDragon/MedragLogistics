package net.medrag.validator;

import net.medrag.dto.CustomerDto;
import net.medrag.model.domain.entity.Customer;
import net.medrag.model.service.dto.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Standard validator for class {@link CustomerDto}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class CustomerValidator implements Validator {

    private CustomerService<CustomerDto, Customer> customerService;

    @Autowired
    public void setCustomerService(CustomerService<CustomerDto, Customer> customerService) {
        this.customerService = customerService;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return CustomerDto.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {
        errors.rejectValue("passport", "you.cheat");
    }

    public CustomerDto validate(@Nullable CustomerDto customer, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passport", "notnull.field");

        if (customer.getPassport() != null) {

            CustomerDto dbCustomer = customerService.getDtoByNaturalId(new CustomerDto(), new Customer(), customer.getPassport());

            if (dbCustomer == null) {

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "notnull.field");

                if (!errors.hasErrors()) {
                    Integer id = customerService.addDto(customer, new Customer());
                    customer.setId(id);
                }

            } else {

                customer = dbCustomer;

            }

        }

        return customer;
    }
}

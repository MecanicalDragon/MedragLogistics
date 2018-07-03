package net.medrag.model.validator;

import net.medrag.dto.CustomerDto;
import net.medrag.model.domain.entity.Customer;
import net.medrag.model.service.CustomerService;
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
        return Customer.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {
        CustomerDto customer = (CustomerDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passport", "notnull.field");


    }

    public CustomerDto validate(@Nullable CustomerDto customer, Errors errors) {

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passport", "notnull.field");

        if (customer.getPassport() != null) {
            CustomerDto dbCustomer = customerService.getDtoByNaturalId(new CustomerDto(), new Customer(), customer.getPassport());

            if (dbCustomer == null) {

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "notnull.field");

            } else {
                customer.setId(dbCustomer.getId());
                if (customer.getName() == null) {
                    customer.setName(dbCustomer.getName());
                }
                if (customer.getSurname() == null) {
                    customer.setSurname(dbCustomer.getSurname());
                }
                if (customer.getEmail() == null) {
                    customer.setEmail(dbCustomer.getEmail());
                }
                if (customer.getPhone() == null) {
                    customer.setPhone(dbCustomer.getPhone());
                }

            }

        }

        return customer;
    }
}

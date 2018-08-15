package net.medrag.validator;

import net.medrag.domain.dto.CustomerDto;
import net.medrag.domain.entity.Customer;
import net.medrag.service.MedragServiceException;
import net.medrag.service.dto.api.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;

/**
 * Standard validator for class {@link CustomerDto}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Component
public class CustomerValidator{

    private CustomerService<CustomerDto, Customer> customerService;

    @Autowired
    public void setCustomerService(CustomerService<CustomerDto, Customer> customerService) {
        this.customerService = customerService;
    }

    public void validate(@Nullable Object target, Errors errors) throws MedragServiceException {

        CustomerDto customer = (CustomerDto) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "passport", "notnull.field");

        if (customer.getPassport() != null) {

            CustomerDto dbCustomer = customerService.getDtoByNaturalId(new CustomerDto(), new Customer(), customer.getPassport());

            if (dbCustomer == null) {

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "notnull.field");

                ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "notnull.field");

                if(customer.getEmail().trim().length() > 0){
                    if (!customer.getEmail().matches("\\w+@\\w+\\.\\w+")){
                        errors.rejectValue("email", "not.email");
                    }
                } else {
                    customer.setEmail(null);
                }

                if (!errors.hasErrors()) {
                    Integer id = customerService.addDto(customer, new Customer());
                    customer.setId(id);
                }

            } else {

                customer.setId(dbCustomer.getId());
                customer.setName(dbCustomer.getName());
                customer.setSurname(dbCustomer.getSurname());
                customer.setEmail(dbCustomer.getEmail());
                customer.setPhone(dbCustomer.getPhone());

            }

        }
    }

}

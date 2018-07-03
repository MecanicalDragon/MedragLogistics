package net.medrag.model.service;

import net.medrag.dto.CustomerDto;
import net.medrag.model.domain.entity.Customer;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Standard service implementation for employment DAO interface{@link net.medrag.model.dao.CustomerDao},
 * working with domain object, that represents a {@link Customer}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class CustomerServiceImpl<D extends CustomerDto, E extends Customer> extends DTOServiceImpl<D, E>
        implements CustomerService<D, E> {

    private final String implementation = "customerDaoImpl";

}

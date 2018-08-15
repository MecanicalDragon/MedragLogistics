package net.medrag.service.dto.impl;

import net.medrag.dao.api.CustomerDao;
import net.medrag.domain.dto.CustomerDto;
import net.medrag.domain.entity.Customer;
import net.medrag.service.dto.api.CustomerService;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link CustomerDao},
 * working with domain object, that represents a {@link Customer}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class CustomerServiceImpl<D extends CustomerDto, E extends Customer> extends DTOServiceImpl<D, E>
        implements CustomerService<D, E> {

    private static final String implementation = "customerDaoImpl";

}

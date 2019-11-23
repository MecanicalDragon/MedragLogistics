package net.medrag.service.dto.api;

import net.medrag.dao.api.CustomerDao;
import net.medrag.domain.dto.CustomerDto;
import net.medrag.domain.entity.Customer;

/**
 * Standard service interface for DAO interface{@link CustomerDao},
 * working with domain object {@link Customer}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CustomerService<D extends CustomerDto, E extends Customer> extends DTOService<D, E> {
}

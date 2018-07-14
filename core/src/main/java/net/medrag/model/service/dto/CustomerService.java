package net.medrag.model.service.dto;

import net.medrag.model.dto.CustomerDto;
import net.medrag.model.domain.entity.Customer;

/**
 * Standard service interface for DAO interface{@link net.medrag.model.dao.CustomerDao},
 * working with domain object {@link net.medrag.model.domain.entity.Customer}
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CustomerService<D extends CustomerDto, E extends Customer> extends DTOService<D, E> {
}

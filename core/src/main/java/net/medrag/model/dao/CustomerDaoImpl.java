package net.medrag.model.dao;

import net.medrag.model.domain.entity.Customer;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link Customer}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class CustomerDaoImpl<E extends Customer> extends EntityDaoImpl<E> implements CustomerDao<E> {
}

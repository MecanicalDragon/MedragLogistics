package net.medrag.dao.api;

import net.medrag.domain.entity.Customer;

/**
 * Standard DAO interface for domain object {@link Customer}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CustomerDao<E extends Customer> extends EntityDao<E> {
}

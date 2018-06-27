package net.medrag.model.dao;

import net.medrag.model.domain.entity.Driver;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class DriverDaoImpl<E extends Driver> extends EntityDaoImpl<E> implements DriverDao<E>{
}

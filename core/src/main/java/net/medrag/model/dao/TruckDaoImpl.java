package net.medrag.model.dao;

import net.medrag.model.domain.entity.Truck;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class TruckDaoImpl<E extends Truck> extends EntityDaoImpl<E> implements TruckDao<E> {
}

package net.medrag.model.dao;

import net.medrag.model.domain.entity.Truck;

/**
 * Standard DAO interface for domain object {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface TruckDao<E extends Truck> extends EntityDao<E> {
}

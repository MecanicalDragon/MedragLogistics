package net.medrag.model.dao;

import net.medrag.model.domain.Truck;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object, that represents a {@link Truck}
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class TruckDaoImpl<E extends Truck> extends EntityDaoImpl<E> implements TruckDao<E> {

}

package net.medrag.model.dao;

import net.medrag.model.domain.entity.Orderr;

/**
 * Standard DAO interface implementation for domain object {@link Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public class OrderDaoImpl<E extends Orderr> extends EntityDaoImpl<E> implements OrderDao<E> {
}

package net.medrag.model.dao;

import net.medrag.model.domain.entity.Orderr;
import org.springframework.stereotype.Repository;

/**
 * Standard DAO interface implementation for domain object {@link Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Repository
public class OrderDaoImpl<E extends Orderr> extends EntityDaoImpl<E> implements OrderDao<E> {
}

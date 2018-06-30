package net.medrag.model.service;

import net.medrag.dto.OrderDto;
import net.medrag.model.domain.entity.Orderr;

/**
 * Standard service interface for DAO interface{@link net.medrag.model.dao.OrderDao},
 * working with domain object {@link Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface OrderService<D extends OrderDto, E extends Orderr> extends DTOService<D, E> {
}

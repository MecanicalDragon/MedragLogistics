package net.medrag.service.dto.api;

import net.medrag.dao.api.OrderDao;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.domain.entity.Orderr;

/**
 * Standard service interface for DAO interface{@link OrderDao},
 * working with domain object {@link Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface OrderService<D extends OrderrDto, E extends Orderr> extends DTOService<D, E> {
}

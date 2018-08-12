package net.medrag.model.service.dto;

import net.medrag.model.domain.dto.OrderrDto;
import net.medrag.model.domain.entity.Orderr;

/**
 * Standard service interface for DAO interface{@link net.medrag.model.dao.OrderDao},
 * working with domain object {@link Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface OrderService<D extends OrderrDto, E extends Orderr> extends DTOService<D, E> {
}

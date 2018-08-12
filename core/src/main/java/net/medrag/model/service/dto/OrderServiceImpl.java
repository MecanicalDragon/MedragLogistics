package net.medrag.model.service.dto;

import net.medrag.model.domain.entity.Orderr;
import net.medrag.model.domain.dto.OrderrDto;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link net.medrag.model.dao.OrderDao},
 * working with domain object, that represents a {@link Orderr}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class OrderServiceImpl<D extends OrderrDto, E extends Orderr> extends DTOServiceImpl<D, E>
        implements OrderService<D, E> {

    private static final String implementation = "orderDaoImpl";

}

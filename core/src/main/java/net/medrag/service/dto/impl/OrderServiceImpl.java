package net.medrag.service.dto.impl;

import net.medrag.dao.api.OrderDao;
import net.medrag.domain.entity.Orderr;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.service.dto.api.OrderService;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link OrderDao},
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

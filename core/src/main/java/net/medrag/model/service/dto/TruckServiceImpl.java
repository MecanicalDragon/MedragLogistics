package net.medrag.model.service.dto;

import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.entity.Truck;
import net.medrag.dto.TruckDto;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link TruckDao},
 * working with domain object, that represents a {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class TruckServiceImpl<D extends TruckDto, E extends Truck> extends DTOServiceImpl<D, E> implements TruckService<D, E> {

    private static final String implementation = "truckDaoImpl";

}

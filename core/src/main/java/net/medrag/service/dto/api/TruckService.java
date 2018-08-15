package net.medrag.service.dto.api;

import net.medrag.dao.api.TruckDao;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.dto.TruckDto;

/**
 * Standard service interface for DAO interface{@link TruckDao},
 * working with domain object {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface TruckService<D extends TruckDto, E extends Truck> extends DTOService<D, E>{
}

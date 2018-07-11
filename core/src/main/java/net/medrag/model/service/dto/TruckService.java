package net.medrag.model.service.dto;

import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.entity.Truck;
import net.medrag.dto.TruckDto;

/**
 * Standard service interface for DAO interface{@link TruckDao},
 * working with domain object {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface TruckService<D extends TruckDto, E extends Truck> extends DTOService<D, E>{
}

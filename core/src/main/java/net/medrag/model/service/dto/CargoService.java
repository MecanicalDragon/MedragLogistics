package net.medrag.model.service.dto;

import net.medrag.model.dao.CargoDao;
import net.medrag.dto.CargoDto;
import net.medrag.model.domain.entity.Cargo;

/**
 * Standard service interface for DAO interface{@link CargoDao},
 * working with domain object {@link Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CargoService<D extends CargoDto, E extends Cargo> extends DTOService<D, E>{
}

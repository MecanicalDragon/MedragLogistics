package net.medrag.service.dto.api;

import net.medrag.dao.api.CargoDao;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.entity.Cargo;

/**
 * Standard service interface for DAO interface{@link CargoDao},
 * working with domain object {@link Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CargoService<D extends CargoDto, E extends Cargo> extends DTOService<D, E>{
}

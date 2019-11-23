package net.medrag.service.dto.impl;

import net.medrag.dao.api.CargoDao;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.entity.Cargo;
import net.medrag.service.dto.api.CargoService;
import org.springframework.stereotype.Service;

/**
 * Standard service implementation for employment DAO interface{@link CargoDao},
 * working with domain object, that represents a {@link Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class CargoServiceImpl<D extends CargoDto, E extends Cargo> extends DTOServiceImpl<D, E>
        implements CargoService<D, E> {

    /**
     * String, that denotes correct implementation for generalised superclass
     */
    private static String implementation = "cargoDaoImpl";

}

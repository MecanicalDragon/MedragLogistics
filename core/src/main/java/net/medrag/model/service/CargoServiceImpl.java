package net.medrag.model.service;

import net.medrag.model.dao.CargoDao;
import net.medrag.dto.CargoDto;
import net.medrag.model.domain.entity.Cargo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Standard service implementation for employment DAO interface{@link CargoDao},
 * working with domain object {@link Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class CargoServiceImpl<D extends CargoDto, E extends Cargo> extends DTOServiceImpl<D, E>
        implements CargoService<D, E>{

    private static String implementation = "cargoDaoImpl";

}

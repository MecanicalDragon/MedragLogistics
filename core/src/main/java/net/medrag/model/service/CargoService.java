package net.medrag.model.service;

import net.medrag.model.dao.CargoDao;
import net.medrag.dto.CargoDto;
import net.medrag.model.domain.entity.Cargo;

import java.util.List;

/**
 * Standard service interface for DAO interface{@link CargoDao},
 * working with domain object {@link Cargo}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CargoService {
    void addCargo(CargoDto cargoDto);
    void updateCargoStatus(CargoDto cargoDto);
    void removeCargo(CargoDto cargoDto);
    CargoDto getCargoById(Integer id);
    CargoDto getCargoByName(String name);
    List<CargoDto> getCargoList();

    void testCargo(CargoDto cargoDto);
}

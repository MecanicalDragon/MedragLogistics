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

    private final String implementation = "cargoDaoImpl";

//
//    private CargoDao<Cargo> cargoDao;
//
//    @Autowired
//    public void setCargoDao(CargoDao<Cargo> cargoDao) {
//        this.cargoDao = cargoDao;
//    }
//
//    @Override
//    @Transactional
//    public void addCargo(CargoDto cargoDto) {
//        cargoDao.addEntity(new ModelMapper().map(cargoDto, Cargo.class));
//    }
//
//    @Override
//    @Transactional
//    public void updateCargoStatus(CargoDto cargoDto) {
//        cargoDao.updateEntityStatus(new ModelMapper().map(cargoDto, Cargo.class));
//
//    }
//
//    @Override
//    @Transactional
//    public void removeCargo(CargoDto cargoDto) {
//        cargoDao.removeEntity(new ModelMapper().map(cargoDto, Cargo.class));
//    }
//
//    @Override
//    @Transactional
//    public CargoDto getCargoById(Integer id) {
//        Cargo cargo = cargoDao.getEntityById(new Cargo(), id);
//        return new ModelMapper().map(cargo, CargoDto.class);
//    }
//
//    @Override
//    @Transactional
//    public CargoDto getCargoByName(String name) {
//        Cargo cargo = cargoDao.getEntityByNaturalId(new Cargo(), name);
//        return new ModelMapper().map(cargo, CargoDto.class);
//    }
//
//    @Override
//    @Transactional
//    public List<CargoDto> getCargoList() {
//        return null;
//    }
//
//    @Override
//    @Transactional
//    public void testCargo(CargoDto cargoDto) {
//
//    }
}

package net.medrag.model.service;

import net.medrag.model.dao.CityDao;
import net.medrag.model.dao.DriverDao;
import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.City;
import net.medrag.model.domain.Driver;
import net.medrag.model.domain.Truck;
import net.medrag.model.domain.enums.DriverState;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.DriverDto;
import net.medrag.model.dto.TruckDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard service implementation for employment DAO interface{@link DriverDao},
 * working with domain object, that represents a {@link Driver}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class DriverServiceImpl implements DriverService{


    private DriverDao<Driver> driverDao;

//    private CityDao<City> cityDao;
//
//    private TruckDao<Truck> truckDao;
//
//    @Autowired
//    public void setCityDao(CityDao<City> cityDao) {
//        this.cityDao = cityDao;
//    }
//
//    @Autowired
//    public void setTruckDao(TruckDao<Truck> truckDao) {
//        this.truckDao = truckDao;
//    }

    @Autowired
    public void setDriverDao(DriverDao<Driver> driverDao) {
        this.driverDao = driverDao;
    }

    @Override
    @Transactional
    public void addDriver(DriverDto driverDto) {
        driverDao.addEntity(new ModelMapper().map(driverDto, Driver.class));
    }

    @Override
    @Transactional
    public DriverDto getDriverById(Integer id) {
        return new ModelMapper().map(driverDao.getEntityById(new Driver(), id), DriverDto.class);
    }

    @Override
    @Transactional
    public DriverDto getDriverByPersonalNumber(String id) {
        return new ModelMapper().map(driverDao.getEntityByNaturalId(new Driver(), id), DriverDto.class);
    }

    @Override
    @Transactional
    public void updateDriverStatus(DriverDto driverDto) {
        driverDao.updateEntityStatus(new ModelMapper().map(driverDto, Driver.class));
    }

    @Override
    @Transactional
    public void removeDriver(DriverDto driverDto) {
        driverDao.removeEntity(new ModelMapper().map(driverDto, Driver.class));
    }

    @Override
    @Transactional
    public List<DriverDto> getDriverList() {
        List<Driver> driverList = driverDao.getEntityList(new Driver());
        List<DriverDto> driverDtoList = new ArrayList<>();
        for (Driver driver : driverList) {
            driverDtoList.add(new ModelMapper().map(driver, DriverDto.class));
        }
        return driverDtoList;
    }

    //  BUSINESS LOGIC BELOW

    @Override
    public void testDriver(DriverDto driverDto) {

        addDriver(driverDto);
    }

}

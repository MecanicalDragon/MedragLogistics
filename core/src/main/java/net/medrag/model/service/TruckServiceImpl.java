package net.medrag.model.service;

import net.medrag.dto.Dto;
import net.medrag.model.dao.CityDao;
import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.entity.City;
import net.medrag.model.domain.entity.Entity;
import net.medrag.model.domain.entity.Truck;
import net.medrag.dto.TruckDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard service implementation for employment DAO interface{@link TruckDao},
 * working with domain object, that represents a {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class TruckServiceImpl<D extends TruckDto, E extends Truck> extends DTOServiceImpl<D, E> implements TruckService<D, E> {

    private final String implementation = "truckDaoImpl";

//
//    private TruckDao<Truck> truckDao;
//
//    private CityDao<City> cityDao;
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
//
//    @Override
//    @Transactional
//    public void addTruck(TruckDto truckDto) {
//
//        Truck truck = new ModelMapper().map(truckDto, Truck.class);
////        City city = cityDao.getEntityByNaturalId(new City(), truckDto.getCurrentCity());
////        truck.setCurrentCity(city);
//
//        truckDao.addEntity(truck);
//    }
//
//    @Override
//    @Transactional
//    public void updateTruckStatus(TruckDto truckDto) {
//        truckDao.updateEntityStatus(new ModelMapper().map(truckDto, Truck.class));
//    }
//
//    @Override
//    @Transactional
//    public void removeTruck(TruckDto truckDto) {
//        truckDao.removeEntity(new ModelMapper().map(truckDto, Truck.class));
//    }
//
//    @Override
//    @Transactional
//    public TruckDto getTruckById(Integer id) {
//        return new ModelMapper().map(truckDao.getEntityById(new Truck(), id), TruckDto.class);
//    }
//
//    @Override
//    @Transactional
//    public TruckDto getTruckByRegNumber(String id) {
//        return new ModelMapper().map(truckDao.getEntityByNaturalId(new Truck(), id), TruckDto.class);
//    }
//
//    @Override
//    @Transactional
//    public List<TruckDto> getTruckList() {
//        List<Truck> truckList = truckDao.getEntityList(new Truck());
//        List<TruckDto> truckDtoList = new ArrayList<>();
//        for (Truck truck : truckList) {
//            truckDtoList.add(new ModelMapper().map(truck, TruckDto.class));
//        }
//        return truckDtoList;
//    }
//
//    //  BUSINESS LOGIC BELOW
//
//    @Override
//    public void testTruck(TruckDto truckDto) {
//
//        addTruck(truckDto);
//    }
}

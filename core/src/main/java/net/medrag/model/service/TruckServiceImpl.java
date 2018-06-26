package net.medrag.model.service;

import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.Truck;
import net.medrag.model.dto.CityDto;
import net.medrag.model.dto.TruckDto;
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
public class TruckServiceImpl implements TruckService {

    private TruckDao<Truck> truckDao;

    private CityService cityService;

    @Autowired
    public void setCityService(CityService cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setTruckDao(TruckDao<Truck> truckDao) {
        this.truckDao = truckDao;
    }

    @Override
    @Transactional
    public void addTruck(TruckDto truckDto) {
        truckDao.addEntity(new ModelMapper().map(truckDto, Truck.class));
    }

    @Override
    @Transactional
    public void updateTruckStatus(TruckDto truckDto) {
        truckDao.updateEntityStatus(new ModelMapper().map(truckDto, Truck.class));
    }

    @Override
    @Transactional
    public void removeTruck(TruckDto truckDto) {
        truckDao.removeEntity(new ModelMapper().map(truckDto, Truck.class));
    }

    @Override
    @Transactional
    public TruckDto getTruckById(Integer id) {
        return new ModelMapper().map(truckDao.getEntityById(new Truck(), id), TruckDto.class);
    }

    @Override
    @Transactional
    public TruckDto getTruckByRegNumber(String id) {
        return new ModelMapper().map(truckDao.getEntityByNaturalId(new Truck(), id), TruckDto.class);
    }

    @Override
    @Transactional
    public List<TruckDto> getTruckList() {
        List<Truck> truckList = truckDao.getEntityList(new Truck());
        List<TruckDto> truckDtoList = new ArrayList<>();
        for (Truck truck : truckList) {
            truckDtoList.add(new ModelMapper().map(truck, TruckDto.class));
        }
        return truckDtoList;
    }

    //  BUSINESS LOGIC BELOW

    public void addTruck(String number, String brigade, String capacity, String state, String currentCity) {
        TruckDto truck = new TruckDto();
        truck.setRegNumber(number);
        truck.setBrigadeStr(Integer.valueOf(brigade));
        truck.setCapacity(Integer.valueOf(capacity));
        truck.setState(Boolean.valueOf(state));

        CityDto city = cityService.getCityByName(currentCity);

        truck.setCurrentCity(city);

        addTruck(truck);
    }
}

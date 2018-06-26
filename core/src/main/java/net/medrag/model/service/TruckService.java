package net.medrag.model.service;

import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.Truck;
import net.medrag.model.dto.TruckDto;

import java.util.List;

/**
 * Standard service interface for DAO interface{@link TruckDao},
 * working with domain object, that represents a {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface TruckService {
    void addTruck(TruckDto truckDto);
    void updateTruckStatus(TruckDto truckDto);
    void removeTruck(TruckDto truckDto);
    TruckDto getTruckById(Integer id);
    TruckDto getTruckByRegNumber(String id);
    List<TruckDto> getTruckList();

    public void addTruck(String number, String brigade, String capacity, String state, String currentCity);
}

package net.medrag.model.service;

import net.medrag.dto.Dto;
import net.medrag.model.dao.TruckDao;
import net.medrag.model.domain.entity.Entity;
import net.medrag.model.domain.entity.Truck;
import net.medrag.dto.TruckDto;

import java.util.List;

/**
 * Standard service interface for DAO interface{@link TruckDao},
 * working with domain object {@link Truck}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface TruckService<D extends TruckDto, E extends Truck> extends DTOService<D, E>{
//    void addTruck(TruckDto truckDto);
//    void updateTruckStatus(TruckDto truckDto);
//    void removeTruck(TruckDto truckDto);
//    TruckDto getTruckById(Integer id);
//    TruckDto getTruckByRegNumber(String id);
//    List<TruckDto> getTruckList();
//
//    public void testTruck(TruckDto truckDto);
}

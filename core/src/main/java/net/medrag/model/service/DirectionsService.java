package net.medrag.model.service;

import net.medrag.model.domain.dto.CargoDto;
import net.medrag.model.domain.dto.CityDto;
import net.medrag.model.domain.dto.TruckDto;

/**
 * Interface, responsible for work with google directions api.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DirectionsService {
    Integer[] getTripTime(CityDto departure, CityDto destination) throws MedragServiceException;
    Integer getComletePersent(CargoDto cargo) throws MedragServiceException;
    Integer[] getTripTimeByTruck(TruckDto truck) throws MedragServiceException;
}

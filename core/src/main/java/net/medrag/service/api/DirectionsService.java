package net.medrag.service.api;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.TruckDto;
import net.medrag.service.MedragServiceException;

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

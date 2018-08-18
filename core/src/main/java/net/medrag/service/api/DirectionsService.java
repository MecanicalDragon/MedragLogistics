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

    /**
     * Method calculates distance between two cities and time, needed for getting from one to another.
     * @param departure - departure city.
     * @param destination - destination city.
     * @return - array of integers, that has distance in kms in his first cell and time in minutes in second.
     * @throws MedragServiceException - if google refuses request
     */
    Integer[] getTripTime(CityDto departure, CityDto destination) throws MedragServiceException;

    /**
     * Method calculates percent of cargo delivery completeness.
     * @param cargo - that cargo
     * @return - founded percent
     * @throws MedragServiceException - didn't heared about it
     */
    Integer getComletePersent(CargoDto cargo) throws MedragServiceException;

    /**
     * Method calculates distance between two cities and time, needed for getting from one to another, basing on the truck data.
     * @param truck - data source
     * @return - array of integers, that has distance in kms in his first cell and time in minutes in second.
     * @throws MedragServiceException - if "getTripTime" fails.
     */
    Integer[] getTripTimeByTruck(TruckDto truck) throws MedragServiceException;
}

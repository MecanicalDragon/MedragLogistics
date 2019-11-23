package net.medrag.service.api;

import net.medrag.domain.dto.CityDto;
import net.medrag.service.MedragServiceException;

/**
 * Service is needed for single method of removing city.
 */
public interface CityHandlingService {

    /**
     * Removing city.
     * @param city - city that is able to be removed.
     * @return - true, if it's done or false, if it's can't be done.
     * @throws MedragServiceException - it would be very often, if there would not be this method.
     */
    boolean removeCity(CityDto city) throws MedragServiceException;
}

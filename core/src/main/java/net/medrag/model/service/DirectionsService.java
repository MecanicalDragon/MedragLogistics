package net.medrag.model.service;

import net.medrag.model.dto.CityDto;

/**
 * Standard interface for {@link  }
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface DirectionsService {
    Integer[] getTripTime(CityDto departure, CityDto destination) throws MedragServiceException;
}

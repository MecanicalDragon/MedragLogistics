package net.medrag.service.api;

import net.medrag.domain.dto.CityDto;
import net.medrag.service.MedragServiceException;

public interface CityHandlingService {
    boolean removeCity(CityDto city) throws MedragServiceException;
}

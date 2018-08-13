package net.medrag.model.service;

import net.medrag.model.domain.dto.CityDto;

public interface CityHandlingService {
    boolean removeCity(CityDto city) throws MedragServiceException;
}

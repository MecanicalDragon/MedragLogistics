package net.medrag.model.service;

import net.medrag.model.dao.CityDao;
import net.medrag.dto.CityDto;
import net.medrag.model.domain.entity.City;

/**
 * Standard service interface for DAO interface{@link CityDao},
 * working with domain object {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
public interface CityService<D extends CityDto, E extends City> extends DTOService<D, E> {
}


//public interface CityService {
//    void addCity(CityDto cityDto);
//    void updateCityStatus(CityDto cityDto);
//    void removeCity(CityDto cityDto);
//    CityDto getCityById(Integer id);
//    CityDto getCityByName(String name);
//    List<CityDto> getCityList();
//
//    void testCity(CityDto cityDto);
//}
package net.medrag.model.service;

import net.medrag.model.dao.CityDao;
import net.medrag.model.domain.City;
import net.medrag.model.dto.CityDto;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Standard service implementation for employment DAO interface{@link CityDao},
 * working with domain object, that represents a {@link City}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class CityServiceImpl implements CityService {


    private CityDao<City> cityDao;

    @Autowired
    public void setCityDao(CityDao<City> cityDao) {
        this.cityDao = cityDao;
    }

    @Override
    @Transactional
    public void addCity(CityDto cityDto) {
        cityDao.addEntity(new ModelMapper().map(cityDto, City.class));
    }

    @Override
    @Transactional
    public void updateCityStatus(CityDto cityDto) {
        cityDao.updateEntityStatus(new ModelMapper().map(cityDto, City.class));
    }

    @Override
    @Transactional
    public void removeCity(CityDto cityDto) {
        cityDao.removeEntity(new ModelMapper().map(cityDto, City.class));
    }

    @Override
    @Transactional
    public CityDto getCityById(Integer id) {
        City city = cityDao.getEntityById(new City(), id);
        return new ModelMapper().map(city, CityDto.class);
    }

    @Override
    @Transactional
    public CityDto getCityByName(String name) {
        City city = cityDao.getEntityByNaturalId(new City(), name);
        return new ModelMapper().map(city, CityDto.class);
    }

    @Override
    @Transactional
    public List<CityDto> getCityList() {
        List<City> cityList = cityDao.getEntityList(new City());
        List<CityDto> cityDtoList = new ArrayList<>();
        for (City city : cityList) {
            cityDtoList.add(new ModelMapper().map(city, CityDto.class));
        }
        return cityDtoList;
    }


    //  BUSINESS LOGIC BELOW

    @Override
    public void testCity(CityDto cityDto) {

        addCity(cityDto);
        System.out.println("successfull adding");

        CityDto idCity = getCityById(6);        //change this parameter
        System.out.println("successfull getting by id");
        System.out.println(idCity);

        idCity.setCoordinates_X(666);
        idCity.setCoordinates_Y(666);
        updateCityStatus(idCity);
        System.out.println("successfull updating");
        System.out.println(idCity);

        CityDto huecity= getCityByName("luki");        //change this parameter
        System.out.println("successfull getting by name");
        System.out.println(huecity);

        removeCity(huecity);
        System.out.println("successfull removing");

        List<CityDto> cities = getCityList();
        System.out.println(cities);
        System.out.println("successfull list getting");
    }

}

package net.medrag.service.dto.impl;

import net.medrag.domain.dto.CityDto;
import net.medrag.domain.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceImplTest {

    private CityServiceImpl<CityDto, City> cityService;

    @Test
    public void nothingToTest(){
        cityService = new CityServiceImpl<>();
    }

}
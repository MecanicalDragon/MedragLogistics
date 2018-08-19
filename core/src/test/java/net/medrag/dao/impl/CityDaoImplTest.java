package net.medrag.dao.impl;

import net.medrag.dao.api.CityDao;
import net.medrag.domain.entity.City;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CityDaoImplTest {

    private CityDao<City> cityDao;

    @Test
    public void nothingToTest() throws Exception {
        cityDao = new CityDaoImpl<>();
    }

}
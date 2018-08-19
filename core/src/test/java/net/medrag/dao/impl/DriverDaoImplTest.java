package net.medrag.dao.impl;

import net.medrag.dao.api.DriverDao;
import net.medrag.domain.entity.Driver;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class DriverDaoImplTest {

    private DriverDao<Driver> driverDao;

    @Test
    public void nothingToTest() throws Exception {
        driverDao = new DriverDaoImpl<>();
    }

}
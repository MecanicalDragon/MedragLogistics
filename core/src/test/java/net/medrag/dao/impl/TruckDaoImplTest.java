package net.medrag.dao.impl;

import net.medrag.dao.api.TruckDao;
import net.medrag.domain.entity.Truck;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class TruckDaoImplTest {

    private TruckDao<Truck> truckTruckDao;

    @Test
    public void nothingToTest() throws Exception {
        truckTruckDao = new TruckDaoImpl<>();
    }

}
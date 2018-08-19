package net.medrag.dao.impl;

import net.medrag.dao.api.CargoDao;
import net.medrag.domain.entity.Cargo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CargoDaoImplTest {

    private CargoDao<Cargo> cargoDao;

    @Test
    public void nothingToTest() throws Exception {
        cargoDao = new CargoDaoImpl<>();
    }
}
package net.medrag.service.dto.impl;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.entity.Cargo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;


@RunWith(MockitoJUnitRunner.class)
public class CargoServiceImplTest {

    private CargoServiceImpl<CargoDto, Cargo> cargoService;

    @Test
    public void nothingToTest(){
        cargoService = new CargoServiceImpl<>();
    }

}
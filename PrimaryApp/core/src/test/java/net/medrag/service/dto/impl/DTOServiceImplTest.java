package net.medrag.service.dto.impl;

import net.medrag.dao.api.EntityDao;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.entity.Cargo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DTOServiceImplTest {

    @Mock
    private EntityDao<Cargo> entityDao;

    private DTOServiceImpl<CargoDto, Cargo> service;

    private Cargo cargo;

    @Before
    public void setUp() throws Exception {
        service = new DTOServiceImpl<>();
        service.setEntityDao(entityDao);
        cargo = new Cargo();
        cargo.setId(1);
        cargo.setIndex("index");
    }

    @After
    public void tearDown() throws Exception {
        service = null;
        cargo = null;
    }

    @Test
    public void addDto() throws Exception {

        when(entityDao.addEntity(any(Cargo.class))).thenReturn(10);
        int i = service.addDto(new CargoDto(), new Cargo());
        assertEquals(10, i);

    }

    @Test
    public void removeDto() throws Exception {

        doNothing().when(entityDao).removeEntity(any(Cargo.class));
        service.removeDto(new CargoDto(), new Cargo());
        verify(entityDao).removeEntity(any(Cargo.class));

    }

    @Test
    public void getDtoById() throws Exception {

        when(entityDao.getEntityById(any(Cargo.class), anyInt())).thenReturn(cargo);
        CargoDto dtoById = service.getDtoById(new CargoDto(), new Cargo(), 1);
        CargoDto cargoDto = new CargoDto();
        cargoDto.setId(1);
        assertEquals(cargoDto, dtoById);

    }

    @Test
    public void refreshDto() throws Exception {

        when(entityDao.refreshEntity(any(Cargo.class))).thenReturn(cargo);
        CargoDto cargoDto = new CargoDto();
        cargoDto.setId(1);
        CargoDto cargoDtoRefreshed = service.refreshDto(cargoDto, new Cargo());
        assertEquals(cargoDto, cargoDtoRefreshed);

    }

    @Test
    public void getDtoByNaturalId() throws Exception {

        when(entityDao.getEntityByNaturalId(any(Cargo.class), anyString())).thenReturn(cargo);
        CargoDto extractedCargo = service.getDtoByNaturalId(new CargoDto(), new Cargo(), "index");
        assertNotNull(extractedCargo);
        assertEquals(extractedCargo.getIndex(), "index");

    }

    @Test
    public void getDtoCount() throws Exception {

        when(entityDao.getEntityCount(any(Cargo.class))).thenReturn(5);
        int i = service.getDtoCount(new Cargo());
        assertEquals(i, 5);

    }

    @Test
    public void getDtoList() throws Exception {
        List<Cargo> preparedList = new ArrayList<>();
        preparedList.add(cargo);
        when(entityDao.getEntityList(any(Cargo.class))).thenReturn(preparedList);
        List<CargoDto> list = service.getDtoList(new CargoDto(), new Cargo());
        assertNotNull(list);
        assertEquals(preparedList.size(), list.size());

    }

    @Test
    public void getLastObjects() throws Exception {

        List<Cargo> preparedList = new ArrayList<>();
        preparedList.add(cargo);
        when(entityDao.getLastEntities(any(Cargo.class), anyInt())).thenReturn(preparedList);
        List<CargoDto> list = service.getLastObjects(new CargoDto(), new Cargo(), 50);
        assertEquals(list.size(), preparedList.size());

    }

}
package net.medrag.service.dto.impl;

import net.medrag.dao.api.EntityDao;
import net.medrag.domain.dto.TruckDto;
import net.medrag.domain.entity.Truck;
import net.medrag.domain.enums.TruckStatus;
import net.medrag.service.api.MailService;
import net.medrag.service.api.RabbitService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class TruckServiceImplTest extends DTOServiceImpl {

    @Mock
    private RabbitService rabbitService;

    @Mock
    private EntityDao<Truck> entityDao;

    @Spy
    private TruckServiceImpl<TruckDto, Truck> truckService;

    private TruckDto truck;

    @Before
    public void setUp() throws Exception {

        truckService = new TruckServiceImpl<>();
        truckService.setRabbitService(rabbitService);
        truckService.setEntityDao(entityDao);

        doNothing().when(entityDao).updateEntityStatus(any(Truck.class));
        doNothing().when(rabbitService).sendMessage(anyString());

        truck  = new TruckDto();
        truck.setStatus(TruckStatus.STAY_IDLE);

    }

    @After
    public void tearDown() throws Exception {
        truck = null;
        truckService = null;
    }

    @Test   //Testing rabbitService is involved.
    public void updateDtoStatusWithRabbit() throws Exception {

        truck.setPrevStatus(TruckStatus.IN_USE);
        truckService.updateDtoStatus(truck, new Truck());
        verify(rabbitService).sendMessage(anyString());

    }

    @Test   //Testing rabbitService is not involved.
    public void updateDtoStatusWithoutRabbit() throws Exception {

        truck.setPrevStatus(TruckStatus.STAY_IDLE);
        truckService.updateDtoStatus(truck, new Truck());
        verify(rabbitService, times(0)).sendMessage(anyString());

    }

}
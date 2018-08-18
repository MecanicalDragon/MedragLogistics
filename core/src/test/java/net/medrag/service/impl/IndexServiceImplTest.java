package net.medrag.service.impl;

import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CityDto;
import net.medrag.domain.dto.OrderrDto;
import net.medrag.domain.entity.City;
import net.medrag.service.dto.api.CityService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IndexServiceImplTest {

    @Mock
    private CityService<CityDto, City> cityService;

    @Spy
    private IndexServiceImpl indexService;

    @Test   //Testing indexing order.
    public void indicateOrder() throws Exception {

        indexService = new IndexServiceImpl();
        indexService.setCityService(cityService);

        String index = indexService.indicate(new OrderrDto());
        assertTrue(index.matches("ORD-\\d{12}-\\d{4}"));
    }

    @Test   //Testing indexing cargo.
    public void indicateCargo() throws Exception {

        indexService = new IndexServiceImpl();
        indexService.setCityService(cityService);

        CargoDto cargo = new CargoDto();
        cargo.setDepartureName("departure");
        cargo.setDestinationName("destination");

        CityDto city = new CityDto();
        city.setIndex("CTY");

        when(cityService.getDtoByNaturalId(any(CityDto.class), any(City.class), anyString())).thenReturn(city);

        String index = indexService.indicate(cargo);
        assertTrue(index.matches("CGO-\\d{12}-CTY-CTY-\\d{4}"));
    }

}
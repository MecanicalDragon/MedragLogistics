package net.medrag.service.impl;

import net.medrag.domain.dto.*;
import net.medrag.domain.entity.Cargo;
import net.medrag.domain.entity.Orderr;
import net.medrag.domain.entity.Waypoint;
import net.medrag.domain.enums.CargoState;
import net.medrag.service.api.IndexService;
import net.medrag.service.api.MailService;
import net.medrag.service.api.RabbitService;
import net.medrag.service.dto.api.CargoService;
import net.medrag.service.dto.api.OrderService;
import net.medrag.service.dto.api.WaypointService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderHandlingServiceImplTest {

    @Mock
    private IndexService indexService;

    @Mock
    private CargoService<CargoDto, Cargo> cargoService;

    @Mock
    private OrderService<OrderrDto, Orderr> orderService;

    @Mock
    private MailService mailService;

    @Mock
    private WaypointService<WaypointDto, Waypoint> waypointService;

    @Mock
    private RabbitService rabbitService;

    @Spy
    private OrderHandlingServiceImpl orderHandler;

    @Before
    public void setUp() throws Exception {

        orderHandler = new OrderHandlingServiceImpl();
        orderHandler.setCargoService(cargoService);
        orderHandler.setIndexService(indexService);
        orderHandler.setMailService(mailService);
        orderHandler.setOrderService(orderService);
        orderHandler.setRabbitService(rabbitService);
        orderHandler.setWaypointService(waypointService);

        doNothing().when(rabbitService).sendCargo(any(CargoDto.class));


    }

    @After
    public void tearDown() throws Exception {

        orderHandler = null;
    }

    @Test
    public void compileOrderWithNullEmail() throws Exception {

        when(indexService.indicate(any(Dto.class))).thenReturn("index");
        when(cargoService.addDto(any(CargoDto.class), any(Cargo.class))).thenReturn(1);

        CustomerDto customer = new CustomerDto();
        List<CargoDto> cargoes = Stream.of(new CargoDto(), new CargoDto(), new CargoDto()).collect(Collectors.toList());

        orderHandler.compileOrder(cargoes, customer);

        verify(indexService, times(4)).indicate(any(Dto.class));
        verify(orderService, times(1)).addDto(any(OrderrDto.class), any(Orderr.class));
        verify(cargoService, times(3)).addDto(any(CargoDto.class), any(Cargo.class));
        verify(rabbitService, times(3)).sendCargo(any(CargoDto.class));
        verify(mailService, never()).sendTakenOrderMail(any(OrderrDto.class));
    }

    @Test
    public void compileOrderWithNotNullEmail() throws Exception {

        when(indexService.indicate(any(Dto.class))).thenReturn("index");
        when(cargoService.addDto(any(CargoDto.class), any(Cargo.class))).thenReturn(1);
        doNothing().when(mailService).sendTakenOrderMail(any(OrderrDto.class));

        CustomerDto customer = new CustomerDto();
        customer.setEmail("example@example.com");
        List<CargoDto> cargoes = Stream.of(new CargoDto(), new CargoDto(), new CargoDto()).collect(Collectors.toList());

        orderHandler.compileOrder(cargoes, customer);

        verify(indexService, times(4)).indicate(any(Dto.class));
        verify(orderService, times(1)).addDto(any(OrderrDto.class), any(Orderr.class));
        verify(cargoService, times(3)).addDto(any(CargoDto.class), any(Cargo.class));
        verify(rabbitService, times(3)).sendCargo(any(CargoDto.class));
        verify(mailService).sendTakenOrderMail(any(OrderrDto.class));
    }

    @Test
    public void deliverCargoCompleteOrder() throws Exception {

        doNothing().when(waypointService).removeDto(any(WaypointDto.class), any(Waypoint.class));
        doNothing().when(cargoService).updateDtoStatus(any(CargoDto.class), any(Cargo.class));

        CargoDto deliveredCargo = new CargoDto();
        List<WaypointDto> route = Stream.of(new WaypointDto(), new WaypointDto(), new WaypointDto()).collect(Collectors.toList());
        deliveredCargo.setRoute(route);

        OrderrDto order = new OrderrDto();

        List<CargoDto> cargoes = Stream.of(new CargoDto(), new CargoDto(), new CargoDto()).collect(Collectors.toList());
        cargoes.forEach(c -> c.setState(CargoState.DELIVERED));
        cargoes.forEach(c -> c.setOrderr(order));
        cargoes.add(deliveredCargo);

        order.setCargoes(cargoes);
        deliveredCargo.setOrderr(order);

        orderHandler.deliverCargo(deliveredCargo);

        verify(waypointService, times(3)).removeDto(any(WaypointDto.class), any(Waypoint.class));
        verify(cargoService, times(2)).updateDtoStatus(any(CargoDto.class), any(Cargo.class));
        verify(rabbitService, times(4)).sendCargo(any(CargoDto.class));
    }

    @Test
    public void deliverCargoDoNotCompleteOrder() throws Exception {

        doNothing().when(waypointService).removeDto(any(WaypointDto.class), any(Waypoint.class));
        doNothing().when(cargoService).updateDtoStatus(any(CargoDto.class), any(Cargo.class));

        CargoDto deliveredCargo = new CargoDto();
        List<WaypointDto> route = Stream.of(new WaypointDto(), new WaypointDto(), new WaypointDto()).collect(Collectors.toList());
        deliveredCargo.setRoute(route);

        OrderrDto order = new OrderrDto();

        List<CargoDto> cargoes = Stream.of(new CargoDto(), new CargoDto(), new CargoDto()).collect(Collectors.toList());
        cargoes.forEach(c -> c.setState(CargoState.PREPARED));
        cargoes.forEach(c -> c.setOrderr(order));
        cargoes.add(deliveredCargo);

        order.setCargoes(cargoes);
        deliveredCargo.setOrderr(order);

        orderHandler.deliverCargo(deliveredCargo);

        verify(waypointService, times(3)).removeDto(any(WaypointDto.class), any(Waypoint.class));
        verify(cargoService, times(1)).updateDtoStatus(any(CargoDto.class), any(Cargo.class));
        verify(rabbitService, times(1)).sendCargo(any(CargoDto.class));
    }

}
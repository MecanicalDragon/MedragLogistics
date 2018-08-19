package net.medrag.service.dto.impl;

import net.medrag.domain.dto.OrderrDto;
import net.medrag.domain.entity.Orderr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderServiceImplTest {

    private OrderServiceImpl<OrderrDto, Orderr> orderService;

    @Test
    public void nothingToTest(){
        orderService = new OrderServiceImpl<>();
    }

}
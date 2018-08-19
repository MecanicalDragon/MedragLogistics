package net.medrag.dao.impl;

import net.medrag.dao.api.OrderDao;
import net.medrag.domain.entity.Orderr;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class OrderDaoImplTest {

    private OrderDao<Orderr> orderDao;

    @Test
    public void nothingToTest() throws Exception {
        orderDao = new OrderDaoImpl<>();
    }

}
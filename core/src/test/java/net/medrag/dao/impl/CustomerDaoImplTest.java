package net.medrag.dao.impl;

import net.medrag.dao.api.CustomerDao;
import net.medrag.domain.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerDaoImplTest {

    private CustomerDao<Customer>customerDao;

    @Test
    public void nothingToTest() throws Exception {
        customerDao = new CustomerDaoImpl<>();
    }
}
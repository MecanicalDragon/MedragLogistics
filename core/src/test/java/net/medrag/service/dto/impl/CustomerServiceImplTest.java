package net.medrag.service.dto.impl;

import net.medrag.dao.api.CustomerDao;
import net.medrag.domain.dto.CustomerDto;
import net.medrag.domain.entity.Customer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    private CustomerServiceImpl<CustomerDto, Customer> customerService;

    @Mock
    CustomerDao<Customer>customerDao;

    @Test
    public void nothingToTest(){
        customerService = new CustomerServiceImpl<>();

    }

}
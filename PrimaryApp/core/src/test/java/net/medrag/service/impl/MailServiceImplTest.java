package net.medrag.service.impl;

import net.medrag.domain.dto.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mail.javamail.JavaMailSender;

import javax.mail.internet.MimeMessage;

import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MailServiceImplTest {

    @Mock
    private MimeMessage message;

    @Mock
    private JavaMailSender mailSender;

    @Spy
    private MailServiceImpl mailService;

    @Before
    public void setUp() throws Exception {
        mailService = new MailServiceImpl();
        mailService.setMailSender(mailSender);
        Field sendEmails = mailService.getClass().getDeclaredField("sendEmails");
        sendEmails.setAccessible(true);
        sendEmails.set(mailService, true);
        doNothing().when(mailSender).send(any(MimeMessage.class));
        when(mailSender.createMimeMessage()).thenReturn(message);
    }

    @After
    public void tearDown() throws Exception {
        mailService = null;
    }

    @Test
    public void sendLoginPasswordEmail() throws Exception {

        mailService.sendLoginPasswordEmail("example@example.com", "newUser", "password", "anyType");
        verify(mailSender).send(any(MimeMessage.class));

    }

    @Test
    public void sendDeliveredCargoEmail() throws Exception {

        CustomerDto customer = new CustomerDto();
        customer.setName("Ivan");
        customer.setSurname("Petrov");
        customer.setEmail("example@example.com");

        OrderrDto order = new OrderrDto();
        order.setIndex("ORD-987654321");

        CargoDto cargo = new CargoDto();
        cargo.setOwner(customer);
        cargo.setOrderr(order);
        cargo.setName("pants");
        cargo.setIndex("CGO-123456789");
        cargo.setDestinationName("Freak-city");

        mailService.sendDeliveredCargoEmail(cargo);
        verify(mailSender).send(any(MimeMessage.class));
    }

    @Test
    public void sendTakenOrderMail() throws Exception {

        CustomerDto customer = new CustomerDto();
        customer.setName("Ivan");
        customer.setSurname("Petrov");
        customer.setEmail("example@example.com");

        OrderrDto order = new OrderrDto();
        order.setIndex("ORD-987654321");

        CargoDto cargo = new CargoDto();
        cargo.setName("garbage");
        cargo.setIndex("CGO-123456789");
        cargo.setDepartureName("Punk-city");
        cargo.setDestinationName("Freak-city");

        order.setOwner(customer);
        order.setCargoes(Stream.of(cargo).collect(Collectors.toList()));

        mailService.sendTakenOrderMail(order);
        verify(mailSender).send(any(MimeMessage.class));

    }

    @Test
    public void sendCompiledRouteMesaage() throws Exception {

        TruckDto truck = new TruckDto();
        truck.setRegNumber("AB12345");

        DriverDto driver = new DriverDto();
        driver.setEmail("example@example.com");
        driver.setName("Petr");
        driver.setSurname("Ivanov");
        driver.setCurrentTruck(truck);

        mailService.sendCompiledRouteMesaage(driver, "Despair-city");
        verify(mailSender).send(any(MimeMessage.class));

    }

    @Test
    public void sendWorkedTimeLimitMail() throws Exception {

        DriverDto driver = new DriverDto();
        driver.setEmail("example@example.com");
        driver.setName("Petr");
        driver.setSurname("Ivanov");

        mailService.sendWorkedTimeLimitMail(driver);
        verify(mailSender).send(any(MimeMessage.class));
    }

}
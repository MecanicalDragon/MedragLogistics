package net.medrag.service.impl;

import com.rabbitmq.client.*;
import net.medrag.domain.dto.CargoDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RabbitServiceImplTest {

    @Mock
    private static ConnectionFactory rabbit;

    @Mock
    private Connection connection;

    @Mock
    private Channel channel;

    @Mock
    private AMQP.Exchange.DeclareOk declareOk;

    @Spy
    private RabbitServiceImpl rabbitService;

    @Before
    public void setUp() throws Exception {

        rabbitService = new RabbitServiceImpl();
        when(rabbit.newConnection()).thenReturn(connection);
        when(connection.createChannel()).thenReturn(channel);
    }

    @After
    public void tearDown() throws Exception {

        rabbitService = null;
        RabbitServiceImpl.setRabbit(null);
    }

    @Test   //Testing run.
    public void run() throws Exception {

        rabbitService.run();
        verify(rabbit, never()).setHost(anyString());

    }

    @Test   //Sending stats.
    public void sendMessage() throws Exception {

        when(channel.exchangeDeclare(anyString(), any(BuiltinExchangeType.class))).thenReturn(declareOk);
        doNothing().when(channel).basicPublish(anyString(), anyString(), eq(null), any(byte[].class));

        RabbitServiceImpl.setRabbit(rabbit);
        rabbitService.sendMessage("message");
        verify(channel).exchangeDeclare(anyString(), any(BuiltinExchangeType.class));
        verify(channel).basicPublish(anyString(), anyString(), eq(null), any(byte[].class));
    }

    @Test   //Not sending stats.
    public void sendMessageNullRabbit() throws Exception {

        rabbitService.sendMessage("message");
        verify(channel, never()).exchangeDeclare(anyString(), any(BuiltinExchangeType.class));
        verify(channel, never()).basicPublish(anyString(), anyString(), eq(null), any(byte[].class));
    }

    @Test   //Sending cargo.
    public void sendCargo() throws Exception {

        when(channel.exchangeDeclare(anyString(), any(BuiltinExchangeType.class))).thenReturn(declareOk);
        doNothing().when(channel).basicPublish(anyString(), anyString(), eq(null), any(byte[].class));

        RabbitServiceImpl.setRabbit(rabbit);
        rabbitService.sendCargo(new CargoDto());
        verify(channel).exchangeDeclare(anyString(), any(BuiltinExchangeType.class));
        verify(channel).basicPublish(anyString(), anyString(), eq(null), any(byte[].class));
    }

    @Test   //Not sending cargo.
    public void sendCargoNullRabbit() throws Exception {

        rabbitService.sendCargo(new CargoDto());
        verify(channel, never()).exchangeDeclare(anyString(), any(BuiltinExchangeType.class));
        verify(channel, never()).basicPublish(anyString(), anyString(), eq(null), any(byte[].class));
    }

}
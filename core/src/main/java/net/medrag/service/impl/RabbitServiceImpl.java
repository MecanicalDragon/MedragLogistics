package net.medrag.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import net.medrag.domain.dto.CargoDto;
import net.medrag.domain.dto.CargoForm;
import net.medrag.service.MedragServiceException;
import net.medrag.service.api.RabbitService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * MQ service. Sends information to Watcher app about cargoes, orders, trucks and drivers changes in the database.
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    private static ConnectionFactory rabbit;

    private static final String RESOURCES = "resources";

    private static final String DELIVERY = "delivery";

    static void setRabbit(ConnectionFactory rabbit) {
        RabbitServiceImpl.rabbit = rabbit;
    }

    /**
     * Method is being called through the fist request from the Watcher app. Activates RabbitMQ.
     *
     * @throws MedragServiceException - nope.
     */
    @Override
    public void run() throws MedragServiceException {

        if (rabbit == null) {
            rabbit = new ConnectionFactory();
            rabbit.setHost("localhost");
        }
    }

    /**
     * Method sends message with changes in truck or driver statuses.
     *
     * @param message - changes in truck or driver.
     * @throws MedragServiceException - never been.
     */
    @Override
    public void sendMessage(String message) throws MedragServiceException {
        if (rabbit != null) {
            try (Connection connection = rabbit.newConnection();
                 Channel channel = connection.createChannel()) {

                channel.exchangeDeclare(RESOURCES, BuiltinExchangeType.FANOUT);
                channel.basicPublish(RESOURCES, "", null, message.getBytes("UTF-8"));
//                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException | TimeoutException e) {
                throw new MedragServiceException(e);
            }
        }
    }

    /**
     * Method sends to Watcher app the changed cargo.
     *
     * @param cargo - changed cargo.
     * @throws MedragServiceException - as a previous method.
     */
    @Override
    public void sendCargo(CargoDto cargo) throws MedragServiceException {
        if (rabbit != null) {
            try (Connection connection = rabbit.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(DELIVERY, BuiltinExchangeType.FANOUT);

                CargoForm cargoForm = new ModelMapper().map(cargo, CargoForm.class);
                String message = new ObjectMapper().writeValueAsString(cargoForm);

                channel.basicPublish(DELIVERY, "", null, message.getBytes("UTF-8"));
//                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException | TimeoutException e) {
                throw new MedragServiceException(e);
            }
        }
    }
}

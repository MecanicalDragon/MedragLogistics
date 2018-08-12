package net.medrag.model.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import net.medrag.model.domain.dto.CargoDto;
import net.medrag.model.domain.dto.CargoForm;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * {@link}
 *
 * @author Stanislav Tretyakov
 * @version 1.0
 */
@Service
public class RabbitServiceImpl implements RabbitService {

    private static ConnectionFactory rabbit;

    private static final String RESOURCES = "resources";

    private static final String DELIVERY = "delivery";

    @Override
    public void run() throws MedragServiceException{

        if (rabbit == null) {
            rabbit = new ConnectionFactory();
            rabbit.setHost("localhost");
        }
    }

    @Override
    public void sendMessage(String message) throws MedragServiceException {
        if (rabbit != null) {
            try (Connection connection = rabbit.newConnection();
                 Channel channel = connection.createChannel()) {

                channel.exchangeDeclare(RESOURCES, BuiltinExchangeType.FANOUT);
                channel.basicPublish(RESOURCES, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException | TimeoutException e) {
                throw new MedragServiceException(e);
            }
        }
    }

    @Override
    public void sendCargo(CargoDto cargo) throws MedragServiceException {
        if (rabbit != null) {
            try (Connection connection = rabbit.newConnection();
                 Channel channel = connection.createChannel()) {
                channel.exchangeDeclare(DELIVERY, BuiltinExchangeType.FANOUT);

                CargoForm cargoForm = new ModelMapper().map(cargo, CargoForm.class);
                String message = new ObjectMapper().writeValueAsString(cargoForm);

                channel.basicPublish(DELIVERY, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException | TimeoutException e) {
                throw new MedragServiceException(e);
            }
        }
    }
}

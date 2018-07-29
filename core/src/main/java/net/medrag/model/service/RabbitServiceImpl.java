package net.medrag.model.service;

import com.rabbitmq.client.*;
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

    private static final String EXCHANGE_NAME = "watcher";

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

                channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
                channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
                System.out.println(" [x] Sent '" + message + "'");
            } catch (IOException | TimeoutException e) {
                throw new MedragServiceException(e);
            }
        }
    }
}

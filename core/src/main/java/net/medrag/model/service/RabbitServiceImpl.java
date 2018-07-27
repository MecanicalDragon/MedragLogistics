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

    private static Thread rabbit;

    private static final String EXCHANGE_NAME = "watcher";

    @Override
    public void run() throws MedragServiceException {

        if (rabbit == null || !rabbit.isAlive()) {

            rabbit = new Thread(() -> {
                ConnectionFactory factory = new ConnectionFactory();
                factory.setHost("localhost");
                try (Connection connection = factory.newConnection();
                     Channel channel = connection.createChannel()) {

                    channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
                    String message = "message number ";

                    for (int i = 0; true; i++) {
                        String msg = message + i;
                        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes("UTF-8"));
                        System.out.println(" [x] Sent '" + msg + "'");
                        Thread.sleep(15000);
                    }
                } catch (TimeoutException | IOException | InterruptedException e) {
                }
            });
            rabbit.setDaemon(true);
            rabbit.start();
        }
    }
}

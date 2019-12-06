package net.medrag.beans;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.*;
import net.medrag.model.CargoForm;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.push.Push;
import javax.faces.push.PushContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Named("Main")
@ViewScoped
public class MainBean implements Serializable {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MainBean.class);
    private static final String RESOURCES = "resources";
    private static final String DELIVERY = "delivery";
    private static ConnectionFactory rabbit;

    private List<CargoForm> cargoes;
    private List<Integer> stats;

    @Inject
    @Push
    PushContext statsChanges;

    @Inject
    @Push
    PushContext cargoesChanges;

//    Getters and setters

    public List<CargoForm> getCargoes() {
        return cargoes;
    }

    public void setCargoes(List<CargoForm> cargoes) {
        this.cargoes = cargoes;
    }

    public List<Integer> getStats() {
        return stats;
    }

    public void setStats(List<Integer> stats) {
        this.stats = stats;
    }



    @PostConstruct
    public void init() {

//        Initialize and fill the cargoes
        try (InputStream is = new URL("http://primary-app:8080/watch/cargoes").openStream()) {
            JsonNode response = new ObjectMapper().readTree(is);
            cargoes = new ArrayList<>();
            if (response.isArray()) {
                for (final JsonNode node : response) {
                    CargoForm cargo = new ObjectMapper().treeToValue(node, CargoForm.class);
                    cargoes.add(cargo);
                }
            }

        } catch (IOException e) {
            LOGGER.error("Error while fill the cargoes: ", e);
        }

//        Initialize and fill the stats
        try (InputStream is = new URL("http://primary-app:8080/watch/stats").openStream()) {
            JsonNode response = new ObjectMapper().readTree(is);
            stats = new ArrayList<>();
            if (response.isArray()) {
                for (final JsonNode node : response) {
                    Integer stat = new ObjectMapper().treeToValue(node, Integer.class);
                    stats.add(stat);
                }
            }

        } catch (IOException e) {
            LOGGER.error("Error while fill the stats: ", e);
        }

//        Initialize rabbits
        if (rabbit == null) {
            rabbit = new ConnectionFactory();
            rabbit.setHost("rabbitmq");
            initializeRabbit(RESOURCES, statsChanges);
            initializeRabbit(DELIVERY, cargoesChanges);
        }
    }

    private void initializeRabbit(String exchange, PushContext pushContext){
        try {
            Connection connection = rabbit.newConnection();
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(exchange, BuiltinExchangeType.FANOUT);
            String queueName = channel.queueDeclare().getQueue();
            channel.queueBind(queueName, exchange, "");

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope,
                                           AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println(" [x] Received message: " + message);
                    pushContext.send(message);
                }
            };

            channel.basicConsume(queueName, true, consumer);

        } catch (Exception e) {
            LOGGER.error("Exception in rabbit: ", e);
        }
    }
}
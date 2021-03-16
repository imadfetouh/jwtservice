package com.imadelfetouh.jwtservice;

import com.imadelfetouh.jwtservice.jwt.CreateJWTToken;
import com.imadelfetouh.jwtservice.rabbit.RabbitConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Logger logger = Logger.getLogger(App.class.getName());

    private static final Object monitor = new Object();

    public static void main(String[] args) {

        RabbitConnection rabbitConnection = new RabbitConnection();
        ConnectionFactory connectionFactory = rabbitConnection.getConnectionFactory();

        try(Connection connection = connectionFactory.newConnection();
            Channel channel = connection.createChannel()) {
            channel.queueDeclare(rabbitConnection.getQueueName(), false, false, false, null);

            DeliverCallback deliverCallback = (s, delivery) -> {
                AMQP.BasicProperties properties = new AMQP.BasicProperties()
                        .builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

                logger.log(Level.ALL, "Message received: " + message);

                Map<String, String> claims = new HashMap<>();
                claims.put("userId", message);
                String token = CreateJWTToken.getInstance().create(claims);

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, token.getBytes());
            };

            channel.basicConsume(rabbitConnection.getQueueName(), true, deliverCallback, s -> {});

            startMonitor();

        } catch (TimeoutException | IOException e) {
            logger.log(Level.ALL, e.getMessage());
        }
    }

    private static void startMonitor() {
        while(true) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                    break;
                } catch (InterruptedException e) {
                    logger.log(Level.ALL, e.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}

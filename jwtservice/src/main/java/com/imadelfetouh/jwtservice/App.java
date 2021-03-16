package com.imadelfetouh.jwtservice;

import com.imadelfetouh.jwtservice.jwt.CreateJWTToken;
import com.imadelfetouh.jwtservice.rabbit.RabbitConnection;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public class App {

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

                String message = new String(delivery.getBody(), "UTF-8");
                System.out.println("received id: " + message);

                Map<String, String> claims = new HashMap<>();
                claims.put("userId", message);
                String token = CreateJWTToken.getInstance().create(claims);

                channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, token.getBytes());
            };

            channel.basicConsume(rabbitConnection.getQueueName(), true, deliverCallback, s -> {});

            startMonitor();

        } catch (TimeoutException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void startMonitor() {
        while(true) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                    break;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}

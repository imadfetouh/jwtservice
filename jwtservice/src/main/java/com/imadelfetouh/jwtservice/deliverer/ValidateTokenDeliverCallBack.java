package com.imadelfetouh.jwtservice.deliverer;

import com.imadelfetouh.jwtservice.jwt.ValidateJWTToken;
import com.imadelfetouh.jwtservice.rabbit.RabbitConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ValidateTokenDeliverCallBack implements DeliverCallback {

    private Channel channel;
    private RabbitConfig rabbitConfig;
    private ValidateJWTToken validateJWTToken;

    public ValidateTokenDeliverCallBack(Channel channel) {
        this.channel = channel;
        rabbitConfig = RabbitConfig.getInstance();
        validateJWTToken = ValidateJWTToken.getInstance();
    }

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        AMQP.BasicProperties properties = rabbitConfig.getProperties(delivery.getProperties().getCorrelationId());
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

        String userId = validateJWTToken.getUserId(message);

        channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, userId.getBytes());
    }
}

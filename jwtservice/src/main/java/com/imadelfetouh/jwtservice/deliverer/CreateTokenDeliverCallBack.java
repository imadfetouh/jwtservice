package com.imadelfetouh.jwtservice.deliverer;

import com.imadelfetouh.jwtservice.jwt.CreateJWTToken;
import com.imadelfetouh.jwtservice.rabbit.RabbitConfig;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class CreateTokenDeliverCallBack implements DeliverCallback {

    private Channel channel;
    private RabbitConfig rabbitConfig;
    private CreateJWTToken createJWTToken;

    public CreateTokenDeliverCallBack(Channel channel) {
        this.channel = channel;
        rabbitConfig = RabbitConfig.getInstance();
        createJWTToken = CreateJWTToken.getInstance();
    }

    @Override
    public void handle(String s, Delivery delivery) throws IOException {
        AMQP.BasicProperties properties = rabbitConfig.getProperties(delivery.getProperties().getCorrelationId());
        String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

        Map<String, String> claims = new HashMap<>();
        claims.put("userId", message);
        String token = createJWTToken.create(claims);

        channel.basicPublish("", delivery.getProperties().getReplyTo(), properties, token.getBytes());
    }
}

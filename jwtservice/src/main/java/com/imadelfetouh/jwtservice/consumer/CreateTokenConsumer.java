package com.imadelfetouh.jwtservice.consumer;

import com.imadelfetouh.jwtservice.deliverer.CreateTokenDeliverCallBack;
import com.imadelfetouh.jwtservice.rabbit.Consumer;
import com.imadelfetouh.jwtservice.thread.Monitor;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;

public class CreateTokenConsumer implements Consumer {

    private final String QUEUE_NAME;
    private final Object monitor;

    public CreateTokenConsumer() {
        QUEUE_NAME = "createtoken_queue";
        monitor = new Object();
    }

    @Override
    public void consume(Channel channel) {
        try {
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            DeliverCallback deliverCallback = new CreateTokenDeliverCallBack(channel);
            channel.basicConsume(QUEUE_NAME, true, deliverCallback, s -> {});

            Monitor.getInstance().start(monitor);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

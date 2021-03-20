package com.imadelfetouh.jwtservice.thread;

import com.imadelfetouh.jwtservice.consumer.CreateTokenConsumer;
import com.imadelfetouh.jwtservice.rabbit.RabbitConsumer;

public class CreateTokenThread implements Runnable{

    private RabbitConsumer rabbitConsumer;
    private CreateTokenConsumer createTokenConsumer;

    public CreateTokenThread() {
        rabbitConsumer = new RabbitConsumer();
        createTokenConsumer = new CreateTokenConsumer();
    }

    @Override
    public void run() {
        rabbitConsumer.consume(createTokenConsumer);
    }
}

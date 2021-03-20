package com.imadelfetouh.jwtservice.thread;

import com.imadelfetouh.jwtservice.consumer.ValidateTokenConsumer;
import com.imadelfetouh.jwtservice.rabbit.RabbitConsumer;

public class ValidateTokenThread implements Runnable{

    private RabbitConsumer rabbitConsumer;
    private ValidateTokenConsumer validateTokenConsumer;

    public ValidateTokenThread() {
        rabbitConsumer = new RabbitConsumer();
        validateTokenConsumer = new ValidateTokenConsumer();
    }

    @Override
    public void run() {
        rabbitConsumer.consume(validateTokenConsumer);
    }
}

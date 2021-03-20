package com.imadelfetouh.jwtservice.rabbit;

import com.rabbitmq.client.Channel;

public interface Consumer {

    void consume(Channel channel);
}

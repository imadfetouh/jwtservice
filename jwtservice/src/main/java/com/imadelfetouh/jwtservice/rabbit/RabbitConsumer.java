package com.imadelfetouh.jwtservice.rabbit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class RabbitConsumer extends ChannelHelper {

    private static final Logger logger = Logger.getLogger(RabbitConsumer.class.getName());

    public RabbitConsumer() {
        super();
    }

    public void consume(Consumer consumer) {
        try{
            consumer.consume(getChannel());
        }
        catch (Exception e){
            logger.log(Level.ALL, e.getMessage());
        }
        finally {
            closeChannel();
        }
    }
}

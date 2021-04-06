package com.imadelfetouh.jwtservice;

import com.imadelfetouh.jwtservice.rabbit.RabbitConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class RabbitConfigurationTest {

    @Test
    public void testConstructor() {
        RabbitConfiguration rabbitConfiguration = RabbitConfiguration.getInstance();

        Assertions.assertEquals(rabbitConfiguration, RabbitConfiguration.getInstance());
    }

    @Test
    public void testChannel() {
        RabbitConfiguration rabbitConfiguration = RabbitConfiguration.getInstance();

        Assertions.assertNotNull(rabbitConfiguration.getChannel());
    }
}

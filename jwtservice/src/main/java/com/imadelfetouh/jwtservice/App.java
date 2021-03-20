package com.imadelfetouh.jwtservice;

import com.imadelfetouh.jwtservice.thread.CreateTokenThread;
import com.imadelfetouh.jwtservice.thread.ValidateTokenThread;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class App {

    public static void main(String[] args) {

        Executor executor = Executors.newScheduledThreadPool(2);

        CreateTokenThread createTokenThread = new CreateTokenThread();
        executor.execute(createTokenThread);

        ValidateTokenThread validateTokenThread = new ValidateTokenThread();
        executor.execute(validateTokenThread);
    }
}

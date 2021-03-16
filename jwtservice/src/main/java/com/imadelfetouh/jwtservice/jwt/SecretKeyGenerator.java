package com.imadelfetouh.jwtservice.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecretKeyGenerator {

    private static final SecretKeyGenerator secretKeyGenerator = new SecretKeyGenerator();
    private Key key;

    public static SecretKeyGenerator getInstance(){
        return secretKeyGenerator;
    }

    public Key getKey() {
        if(key == null){
            key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
            return key;
        }
        return key;
    }

}

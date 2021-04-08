package com.imadelfetouh.jwtservice.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.logging.Logger;

public class ValidateJWTToken {

    private static final Logger LOGGER = Logger.getLogger(ValidateJWTToken.class.getName());

    private static final ValidateJWTToken validateJWTToken = new ValidateJWTToken();

    public static ValidateJWTToken getInstance(){
        return validateJWTToken;
    }

    public boolean validate(String jwtToken){
        Key key = SecretKeyGenerator.getInstance().getKey();

        try{
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(jwtToken);
            return true;
        }
        catch (JwtException e) {
            return false;
        }
    }
}

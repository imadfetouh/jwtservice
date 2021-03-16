package com.imadelfetouh.jwtservice.jwt;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;

import java.security.Key;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CreateJWTToken {

    private static CreateJWTToken createToken = new CreateJWTToken();

    public static CreateJWTToken getInstance(){
        return (createToken == null) ? new CreateJWTToken() : createToken;
    }

    public String create(Map<String, String> claims){

        Key key = SecretKeyGenerator.getInstance().getKey();

        Header header = Jwts.header();
        header.setType("JWT");

        return Jwts.builder()
                .setHeader((Map<String, Object>) header)
                .setClaims(claims)
                .setIssuer("Kwetterimad")
                .setIssuedAt(getIssuedAt())
                .setExpiration(getExpiration())
                .signWith(key)
                .compact();
    }

    private Date getIssuedAt(){
        Calendar now = Calendar.getInstance();
        return now.getTime();
    }

    private Date getExpiration(){
        Calendar now = Calendar.getInstance();
        now.add(Calendar.MINUTE, 60);
        return now.getTime();
    }

}

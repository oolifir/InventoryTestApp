package com.airxelerate.inventory.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;

@Component
public class JWTUtil {

    private static final String SUBJECT = "User details";
    private static final String CLAIM_USERNAME = "username";

    @Value("${jwt_secret}")
    private String secret;

    @Value("${jwt_expiration_time}")
    private String expirationTime;

    public String generateToken(String username) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(Integer.parseInt(expirationTime)).toInstant());

        return JWT.create()
                .withSubject(SUBJECT)
                .withClaim(CLAIM_USERNAME, username)
                .withIssuedAt(new Date())
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }

    public String validateTokenAndRetrievClaim(String token) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject(SUBJECT)
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim(CLAIM_USERNAME).asString();
    }
}

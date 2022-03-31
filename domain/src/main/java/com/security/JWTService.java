package com.security;

import com.model.SubscriberModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import static java.time.LocalDateTime.now;

@Service
public class JWTService {

    @Value("${security-jwt-expiration}")
    private String expiration;

    @Value("${security-jwt-signature-key}")
    private String signatureKey;

    public String generateToken(SubscriberModel appUser) {
        long expirationLong = Long.parseLong(expiration);
        LocalDateTime dateHourExpiration = LocalDateTime.now().plusMinutes(expirationLong);
        Instant instant = dateHourExpiration.atZone(ZoneId.systemDefault()).toInstant();
        Date date = Date.from(instant);
        return Jwts
                .builder()
                .setSubject(appUser.getUsername())
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS512, signatureKey)
                .compact();
    }

    private Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(signatureKey)
                .parseClaimsJws(token)
                .getBody();
    }

    public boolean isValid(String token) {
        try {
            Claims claims = getClaims(token);
            Date expirationDate = claims.getExpiration();
            LocalDateTime expiration = expirationDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return !now().isAfter(expiration);
        } catch(Exception e) {
            return false;
        }
    }

    public String getAppUserLogin(String token) {
        return getClaims(token).getSubject();
    }

}

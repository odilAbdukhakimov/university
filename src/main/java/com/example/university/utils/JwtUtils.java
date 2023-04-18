package com.example.university.utils;

import com.example.university.entity.UserEntity;
import com.example.university.service.RedisCacheService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@PropertySource("classpath:application.properties")
@RequiredArgsConstructor
public class JwtUtils {

    @Value("${jwtAccessSecretKey}")
    private String jwtAccessSecretKey;
    @Value("${jwtRefreshSecretKey}")
    private String jwtRefreshSecretKey;
    @Value("${expirationAccessTime}")
    private Long expirationAccessTime;

    @Value("${expirationRefreshTime}")
    private Long expirationRefreshTime;
    private final RedisCacheService redisCacheService;

    public synchronized String generateAccessToken(
            UserEntity userDetails
    ) throws JsonProcessingException {
        String redisKey = redisCacheService.addUser(userDetails);
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtAccessSecretKey)
                .setSubject(redisKey)
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationAccessTime))
                .compact();
    }

    public synchronized String generateRefreshToken(
            UserEntity userDetails
    ) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS512, jwtRefreshSecretKey)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + expirationRefreshTime))
                .compact();
    }

    public synchronized Claims isValidAccessToken(String token) {
        return getAccessClaim(token);
    }

    public synchronized Claims isValidRefreshToken(String token) {
        return getRefreshClaim(token);
    }


    private synchronized Claims getAccessClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtAccessSecretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private synchronized Claims getRefreshClaim(String token) {
        try {
            return Jwts.parser().setSigningKey(jwtRefreshSecretKey).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

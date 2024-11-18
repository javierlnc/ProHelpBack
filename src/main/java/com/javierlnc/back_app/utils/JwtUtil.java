package com.javierlnc.back_app.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.antlr.v4.runtime.Token;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static io.jsonwebtoken.Jwts.*;

@Component
public class JwtUtil {
    @Value("${jwt.secretKey}")
    private String _privateKey;
    @Value("${jwt.expirationTime}")
    private long _expirationTime;
    //Generate jwt token
    private String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(getExpirationDate())
                .claims().add(extraClaims).and()
                .signWith(getSigningKey())
                .compact();
    }
    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public String generateToken (UserDetails userDetails){
        return generateToken(new HashMap<>(), userDetails);
    }
    public boolean isTokenValid (String token, UserDetails userDetails){
        final  String username= extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);

    }
    private Date extractExpiration(String token){
        return  extractClaim(token, Claims::getExpiration);
    }
    private boolean isTokenExpired(String token){
        return extractExpiration(token).before(new Date());
    }
    private <T> T extractClaim (String token, Function<Claims, T> claimsResolvers){
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }
    // Obtener la clave de firma HMAC usando la clave secreta
    private Key getSigningKey(){
        byte[] keyBytes = Decoders.BASE64.decode(_privateKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    // Calcular la fecha de expiración
    private Date getExpirationDate() {
        return new Date(System.currentTimeMillis() + _expirationTime);
    }
}

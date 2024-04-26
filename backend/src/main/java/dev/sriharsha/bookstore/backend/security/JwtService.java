package dev.sriharsha.bookstore.backend.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret}")
    private String JWT_SECRET;

    @Value("${application.security.jwt.expiration}")
    private Long JWT_EXPIRATION;

    public String generateToken(UserDetails userDetails) {
        var authorities = userDetails
                .getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + JWT_EXPIRATION))
                .claim("authorities", authorities)
                .signWith(this.generateKey())
                .compact();
    }

    public final boolean isTokenValid(String token, UserDetails userDetails) {
        return (getUsernameFromToken(token).equals(userDetails.getUsername())
                && !isTokenExpired(token));
    }

    public final String getUsernameFromToken(String token) {
        return this.extractClaims(token).getSubject();
    }

    public final boolean isTokenExpired(String token) {
        return extractClaims(token).getExpiration().before(new Date());
    }

    private final Claims extractClaims(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) this.generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private final Key generateKey() {
        var byteKey = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(byteKey);
    }
}

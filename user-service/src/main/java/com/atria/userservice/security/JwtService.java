package com.atria.userservice.security;

import com.atria.userservice.entity.Role;
import com.atria.userservice.entity.User;
import com.atria.userservice.util.RandomGeneraterUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;

    @Value("${jwt.issuer}")
    private String issuer;

    // 🔹 Extract username
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    // 🔹 Extract any claim
    public <T> T extractClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    private String generateToken(User user) {
        List<String> userRoles = user.getRoles().isEmpty() ? List.of() : user.getRoles().stream().map(Role::getName).toList();
        return Jwts.builder()
                .id(String.valueOf(RandomGeneraterUtil.generateTenDigitNumber()))
                .subject(user.getUsername())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessTokenExpiration))
                .claims(Map.of(
                        "username", user.getUsername(),
                        "roles", userRoles,
                        "type", "access"
                ))
                .signWith(getSigningKey())
                .compact();
    }

    private String generateRefreshToken(User user, String id) {
        return Jwts.builder()
                .id(id)
                .subject(user.getUsername())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + refreshTokenExpiration))
                .claims(Map.of("type", "refresh"))
                .signWith(getSigningKey())
                .compact();
    }


    // 🔹 Validate token
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    // 🔹 Check expiration
    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public boolean isAccessToken(String token){
        return extractAllClaims(token).get("type").equals("access");
    }

    public boolean isRefreshToken(String token){
        return extractAllClaims(token).get("type").equals("refresh");
    }

    // 🔹 Extract expiration
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    // 🔹 Parse claims
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // 🔹 Signing key
    private SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
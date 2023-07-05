package cn.xqhuang.dps.model.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class JwtTokenUtil implements Serializable {
    private String secret;
    private Long expiration;
    private String header;

    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder().setClaims(claims).setExpiration(expirationDate).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();

        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);

    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();

        } catch (Exception e) {
            username = null;

        }
        return username;

    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String token) {
        String refreshedToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshedToken = generateToken(claims);

        } catch (Exception e) {
            refreshedToken = null;

        }
        return refreshedToken;
    }

    public Boolean validateToken(String token, String username1) {
        String username = getUsernameFromToken(token);
        return (username.equals(username1) && !isTokenExpired(token));

    }

    public static void main(String[] args) {
        final Claims claims = Jwts.claims();
        claims.put("name", "huangxiangquan");

        final LocalDateTime currentTime = LocalDateTime.now();
        final LocalDateTime expireTime = currentTime.plus(10, ChronoUnit.MILLIS);

        JwtBuilder jwtBuilder = Jwts.builder()
                .setClaims(claims)
                .setIssuer("zs1")
                .setIssuedAt(Date.from(currentTime.atZone(ZoneId.systemDefault()).toInstant()))
                .setExpiration(Date.from(expireTime.atZone(ZoneId.systemDefault()).toInstant()))
                .signWith(SignatureAlgorithm.HS512, "123456");

        jwtBuilder = jwtBuilder.setId(UUID.randomUUID().toString());
        System.out.println(jwtBuilder.compact());
    }
}
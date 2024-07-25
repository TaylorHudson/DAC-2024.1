package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Service
public class TokenServiceImpl implements TokenService {

    public static final String CLAIM_USERID = "userId";
    public static final String CLAIM_USER_EMAIL = "userEmail";
    public static final String CLAIM_EXPIRATION = "expirationTime";

    @Value("${jwt.expiration}")
    private Long expiration;
    @Value("${jwt.secret}")
    private String secret;

    @Override
    public String generate(User user) {
        LocalDateTime localDateTime = LocalDateTime.now().plusMinutes(expiration);
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        Date date = Date.from(zonedDateTime.toInstant());

        String tokenExpiration = localDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));

        return Jwts.builder()
                .setExpiration(date)
                .setSubject(user.getId().toString())
                .claim(CLAIM_USERID, user.getId())
                .claim(CLAIM_USER_EMAIL, user.getEmail())
                .claim(CLAIM_EXPIRATION, tokenExpiration)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    @Override
    public Claims getClaims(String token) throws ExpiredJwtException {
        return Jwts
                .parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public boolean isValid(String token) {
        if (token == null) {
            return false;
        }

        try {
            Claims claims = getClaims(token);
            LocalDateTime expiration = claims.getExpiration().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
            return LocalDateTime.now().isBefore(expiration);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String getUserName(String token) {
        return (String) getClaims(token).get(CLAIM_USER_EMAIL);
    }

    @Override
    public Long getUserId(String token) {
        return Long.parseLong(getClaims(token).getSubject());
    }

    @Override
    public String extractToken(HttpServletRequest request) {
        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        return authorization.substring("Bearer ".length());
    }
}

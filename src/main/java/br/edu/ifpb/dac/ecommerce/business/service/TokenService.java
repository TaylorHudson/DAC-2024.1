package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface TokenService {
    String generate(User user);
    Claims getClaims(String token) throws ExpiredJwtException;
    boolean isValid(String token);
    String getUserName(String token);
    Long getUserId(String token);
    String extractToken(HttpServletRequest request);
}

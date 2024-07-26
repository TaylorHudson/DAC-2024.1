package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TokenServiceImplTest {

    @InjectMocks
    private TokenServiceImpl tokenService;

    private final String secret = "qwdfgyolikyujthnfgbkuethns";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(tokenService, "expiration", 30L);
        ReflectionTestUtils.setField(tokenService, "secret", secret);
    }

    @Test
    void shouldGenerateTokenSuccessfully() {
        User user = User.builder().id(1L).email("test@example.com").build();

        String token = tokenService.generate(user);

        assertNotNull(token);
        Claims claims = Jwts.parser()
                            .setSigningKey(secret)
                            .parseClaimsJws(token)
                            .getBody();

        assertEquals(user.getEmail(), claims.get(TokenServiceImpl.CLAIM_USER_EMAIL));
        assertTrue(claims.getExpiration().after(new Date()));
    }

    @Test
    void shouldThrowExpiredJwtExceptionWhenTokenIsExpired() {
        User user = User.builder().id(1L).email("test@example.com").build();

        LocalDateTime pastDate = LocalDateTime.now().minusDays(1);
        ZonedDateTime zonedDateTime = pastDate.atZone(ZoneId.systemDefault());
        Date expiredDate = Date.from(zonedDateTime.toInstant());

        String token = Jwts.builder()
                .setExpiration(expiredDate)
                .setSubject(user.getId().toString())
                .claim(TokenServiceImpl.CLAIM_USERID, user.getId())
                .claim(TokenServiceImpl.CLAIM_USER_EMAIL, user.getEmail())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        assertThrows(ExpiredJwtException.class, () -> tokenService.getClaims(token));
    }

    @Test
    void shouldReturnFalseIfTokenIsInvalid() {
        String invalidToken = "invalidToken";

        assertFalse(tokenService.isValid(invalidToken));
    }

    @Test
    void shouldReturnUserNameFromToken() {
        User user = User.builder().id(1L).email("test@example.com").build();
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .claim(TokenServiceImpl.CLAIM_USER_EMAIL, user.getEmail())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        String email = tokenService.getUserName(token);

        assertEquals(user.getEmail(), email);
    }

    @Test
    void shouldReturnUserIdFromToken() {
        User user = User.builder().id(1L).email("test@example.com").build();
        String token = Jwts.builder()
                .setSubject(user.getId().toString())
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();

        Long userId = tokenService.getUserId(token);

        assertEquals(user.getId(), userId);
    }

    @Test
    void shouldExtractTokenFromRequest() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Bearer tokenValue");

        String token = tokenService.extractToken(request);

        assertEquals("tokenValue", token);
    }

    @Test
    void shouldReturnNullIfAuthorizationHeaderIsMissing() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn(null);

        String token = tokenService.extractToken(request);

        assertNull(token);
    }

    @Test
    void shouldReturnNullIfAuthorizationHeaderDoesNotStartWithBearer() {
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getHeader(HttpHeaders.AUTHORIZATION)).thenReturn("Basic someOtherToken");

        String token = tokenService.extractToken(request);

        assertNull(token);
    }
}

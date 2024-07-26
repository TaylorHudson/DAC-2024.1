package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceImplTest {

    @Mock
    private TokenService tokenService;

    @Mock
    private UserService userService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    @Test
    void shouldLoginSuccessfully() {
        String email = "test@example.com";
        String password = "password";
        String token = "generatedToken";
        User user = User.builder().email(email).build();

        when(userService.findByEmail(email)).thenReturn(user);
        when(tokenService.generate(user)).thenReturn(token);

        String result = authenticationService.login(email, password);

        assertEquals(token, result);
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userService, times(1)).findByEmail(email);
        verify(tokenService, times(1)).generate(user);
    }

    @Test
    void shouldGetLoggedUserSuccessfully() {
        User user = User.builder().email("test@example.com").build();
        Authentication authentication = mock(Authentication.class);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        when(authentication.getPrincipal()).thenReturn(user);

        User loggedUser = authenticationService.getLoggedUser();

        assertEquals(user, loggedUser);
        verify(authentication, times(1)).getPrincipal();
    }
}

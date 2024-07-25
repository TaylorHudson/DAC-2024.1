package br.edu.ifpb.dac.ecommerce.business.security.filter;

import br.edu.ifpb.dac.ecommerce.business.service.TokenService;
import br.edu.ifpb.dac.ecommerce.model.entity.User;
import br.edu.ifpb.dac.ecommerce.model.exception.UnauthorizedException;
import br.edu.ifpb.dac.ecommerce.model.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class TokenFilter extends OncePerRequestFilter {

    private final TokenService tokenService;

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String token = tokenService.extractToken(request);

        if (token != null && tokenService.isValid(token)) {
            long id = tokenService.getUserId(token);
            User user = userRepository.findById(id).orElseThrow(UnauthorizedException::new);

            var authentication = new UsernamePasswordAuthenticationToken(
                    user.getEmail(),
                    user.getPassword(),
                    user.getRoles()
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

}
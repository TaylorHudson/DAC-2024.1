package br.edu.ifpb.dac.ecommerce.business.security.config;

import br.edu.ifpb.dac.ecommerce.business.security.filter.TokenFilter;
import br.edu.ifpb.dac.ecommerce.business.service.RoleService;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@AllArgsConstructor
public class SecurityConfiguration {

    private final TokenFilter tokenFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

         http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
         http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
         http.csrf(AbstractHttpConfigurer::disable);
         http.addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);

         http.authorizeHttpRequests(req -> req
                    .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                    .requestMatchers(HttpMethod.POST,"/auth", "/auth/login").permitAll()
                    .requestMatchers(HttpMethod.POST,"/user").permitAll()
                    .requestMatchers("/swagger-ui/**","/v3/api-docs/**").permitAll()
                    .requestMatchers(HttpMethod.DELETE,"/user").hasRole(RoleService.AVAILABLE_ROLES.ADMIN.name())
                    .anyRequest().authenticated()
         );

         http.logout(logout ->
                         logout
                             .clearAuthentication(true)
                             .invalidateHttpSession(true)
                             .logoutUrl("/api/auth/logout")
                             .logoutSuccessHandler((request, response, authentication) -> {})
         );
         return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setExposedHeaders(List.of(HttpHeaders.AUTHORIZATION));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
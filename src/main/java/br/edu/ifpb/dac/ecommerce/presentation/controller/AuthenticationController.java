package br.edu.ifpb.dac.ecommerce.presentation.controller;

import br.edu.ifpb.dac.ecommerce.business.service.AuthenticationService;
import br.edu.ifpb.dac.ecommerce.business.service.TokenService;
import br.edu.ifpb.dac.ecommerce.business.service.UserService;
import br.edu.ifpb.dac.ecommerce.model.entity.User;
import br.edu.ifpb.dac.ecommerce.presentation.controller.contract.AuthenticationApiContract;
import br.edu.ifpb.dac.ecommerce.presentation.dto.LoginRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.LoginResponseDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.TokenValidRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.TokenValidResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.SessionScope;

@SessionScope
@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApiContract {

    private final AuthenticationService authenticationService;
    private final UserService userService;
    private final TokenService tokenService;

    @Override
    public ResponseEntity<LoginResponseDto> login(LoginRequestDto loginRequest) {
        String token = authenticationService.login(loginRequest.email(), loginRequest.password());
        User user = userService.findByEmail(loginRequest.email());

        var response = new LoginResponseDto(token, user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<TokenValidResponseDto> isTokenValid(TokenValidRequestDto tokenValidRequest) {
        boolean isValid = tokenService.isValid(tokenValidRequest.accessToken());
        var response = new TokenValidResponseDto(isValid);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}

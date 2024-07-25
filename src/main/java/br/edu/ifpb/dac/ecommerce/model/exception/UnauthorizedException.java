package br.edu.ifpb.dac.ecommerce.model.exception;

import org.springframework.http.HttpStatus;

import java.util.Map;

public class UnauthorizedException extends EcommerceBusinessException {
    public UnauthorizedException() {
        super(
                "Ops! Verificamos que suas credenciais são inválidas",
                HttpStatus.UNAUTHORIZED,
                Map.of()
        );
    }
}

package br.edu.ifpb.dac.ecommerce.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.Map;

@Getter
public abstract class EcommerceBusinessException extends EcommerceException {
    protected EcommerceBusinessException(
            String message,
            HttpStatus status,
            Map<String, Object> metadata
    ) {
        super(message, status, metadata);
    }

    protected EcommerceBusinessException(
            String message,
            Map<String, Object> metadata
    ) {
        super(message, HttpStatus.BAD_REQUEST, metadata);
    }

    protected EcommerceBusinessException(
            String message
    ) {
        super(
                message,
                HttpStatus.BAD_REQUEST,
                Map.of("description", "Ops! Não conseguimos processar a sua requisição")
        );
    }
}

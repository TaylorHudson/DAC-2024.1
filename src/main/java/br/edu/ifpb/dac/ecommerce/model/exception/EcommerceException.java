package br.edu.ifpb.dac.ecommerce.model.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.Map;

@Getter
public abstract class EcommerceException extends RuntimeException {
    protected final HttpStatus status;
    protected final Map<String, Object> metadata;

    public EcommerceException(
            final String message,
            final HttpStatus status,
            final Map<String, Object> metadata) {
        super(message);
        this.status = status;
        this.metadata = metadata;
    }

}

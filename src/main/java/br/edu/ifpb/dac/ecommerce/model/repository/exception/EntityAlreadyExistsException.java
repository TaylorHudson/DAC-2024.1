package br.edu.ifpb.dac.ecommerce.model.repository.exception;

import br.edu.ifpb.dac.ecommerce.model.exception.EcommerceBusinessException;

import java.util.Map;

public class EntityAlreadyExistsException extends EcommerceBusinessException {
    public EntityAlreadyExistsException() {
        super(
                "Ops! Verificamos que esse cadastro já existe",
                Map.of()
        );
    }
    public EntityAlreadyExistsException(String message) {
        super(
                message,
                Map.of()
        );
    }
}

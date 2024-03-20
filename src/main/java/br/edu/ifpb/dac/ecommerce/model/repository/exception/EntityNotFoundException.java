package br.edu.ifpb.dac.ecommerce.model.repository.exception;

import br.edu.ifpb.dac.ecommerce.model.exception.EcommerceBusinessException;

import java.util.Map;

public class EntityNotFoundException extends EcommerceBusinessException {
    public EntityNotFoundException() {
        super(
                "Ops! Não foi encontrado nenhum registro correspondente com essa busca",
                Map.of()
        );
    }

    public EntityNotFoundException(String message) {
        super(
                message,
                Map.of()
        );
    }
}

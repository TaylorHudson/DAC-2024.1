package br.edu.ifpb.dac.ecommerce.model.repository.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String field) {
        super("Entity with the " + field.trim() + " supplied is not found.");
    }
}

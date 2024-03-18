package br.edu.ifpb.dac.ecommerce.model.repository.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String field) {
        super("Entity with the " + field.trim() + " supplied is already exists.");
    }
}

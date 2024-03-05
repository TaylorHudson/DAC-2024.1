package br.edu.ifpb.dac.persistence.repository.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message) {
        super(message);
    }
}

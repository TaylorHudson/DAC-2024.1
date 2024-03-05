package br.edu.ifpb.dac.persistence.repository.exception;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}

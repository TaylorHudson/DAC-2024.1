package br.edu.ifpb.dac.model.validator;

interface Validator {
    default boolean checkIfIsEmpty(String field) {
        return field == null || field.isEmpty() || field.isBlank();
    }
}

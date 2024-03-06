package br.edu.ifpb.dac.model.validator;

import br.edu.ifpb.dac.model.Category;
import br.edu.ifpb.dac.model.validator.exception.InvalidAttributeException;
import org.springframework.stereotype.Component;

@Component
public class CategoryValidatorImpl implements CategoryValidator {
    @Override
    public void validate(Category category) {
        validateFields(category);
    }

    private void validateFields(Category category) {
        if (checkIfIsEmpty(category.getName()))
            throw new InvalidAttributeException("Invalid attribute value name");
        if (checkIfIsEmpty(category.getDescription()))
            throw new InvalidAttributeException("Invalid attribute value description");
    }
}

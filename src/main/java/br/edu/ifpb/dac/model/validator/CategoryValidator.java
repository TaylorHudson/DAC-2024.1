package br.edu.ifpb.dac.model.validator;

import br.edu.ifpb.dac.model.Category;

public interface CategoryValidator extends Validator {
    void validate(Category category);
}

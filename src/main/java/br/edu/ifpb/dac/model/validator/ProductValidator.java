package br.edu.ifpb.dac.model.validator;

import br.edu.ifpb.dac.model.Product;

public interface ProductValidator extends Validator {
    void validate(Product product);
}

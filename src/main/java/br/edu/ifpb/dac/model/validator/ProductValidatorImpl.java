package br.edu.ifpb.dac.model.validator;

import br.edu.ifpb.dac.model.Product;
import br.edu.ifpb.dac.model.validator.exception.InvalidAttributeException;
import org.springframework.stereotype.Component;

@Component
public class ProductValidatorImpl implements ProductValidator {
    @Override
    public void validate(Product product) {
        validatePrice(product.getPrice());
    }

    private void validatePrice(Double price) {
        if (price == null || price <= 0)
            throw new InvalidAttributeException("Invalid attribute value price");
    }
}

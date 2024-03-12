package br.edu.ifpb.dac.model.service;

import br.edu.ifpb.dac.model.Product;
import br.edu.ifpb.dac.model.validator.ProductValidator;
import br.edu.ifpb.dac.model.repository.ProductRepository;
import br.edu.ifpb.dac.model.repository.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.model.repository.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductValidator productValidator;

    @Override
    public Product save(Product newProduct) {
        productValidator.validate(newProduct);
        if (productRepository.existsByName(newProduct.getName()))
            throw new EntityAlreadyExistsException("Entity with the name supplied already exists");

        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id));
    }

    @Override
    public Product update(Long id, Product updatedProduct) {
        productValidator.validate(updatedProduct);
        if (productRepository.existsByName(updatedProduct.getName()))
            throw new EntityAlreadyExistsException("Entity with the name supplied already exists");

        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id));
        productRepository.delete(product);
    }
}

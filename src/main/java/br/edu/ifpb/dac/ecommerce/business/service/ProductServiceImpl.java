package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import br.edu.ifpb.dac.ecommerce.model.repository.ProductRepository;
import br.edu.ifpb.dac.ecommerce.model.repository.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.ecommerce.model.repository.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product save(Product newProduct) {
        if (productRepository.existsByName(newProduct.getName()))
            throw new EntityAlreadyExistsException("name");

        return productRepository.save(newProduct);
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("id"));
    }

    @Override
    public Product update(Long id, Product updatedProduct) {
        productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("id"));
        if (productRepository.existsByName(updatedProduct.getName()))
            throw new EntityAlreadyExistsException("name");

        updatedProduct.setId(id);
        return productRepository.save(updatedProduct);
    }

    @Override
    public void delete(Long id) {
        var product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("id"));
        productRepository.delete(product);
    }
}

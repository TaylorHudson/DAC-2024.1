package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Product;

import java.util.List;

public interface ProductService {
    Product save(Product newProduct);
    List<Product> getProducts();
    Product getProductById(Long id);
    Product update(Long id, Product updatedProduct);
    void delete(Long id);
}

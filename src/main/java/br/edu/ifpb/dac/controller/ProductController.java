package br.edu.ifpb.dac.controller;

import br.edu.ifpb.dac.model.Product;
import br.edu.ifpb.dac.model.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    public Product save(Product product) {
        return productService.save(product);
    }

    public List<Product> getProducts() {
        return productService.getProducts();
    }

    public Product getProductById(Long id) {
        return productService.getProductById(id);
    }

    public Product update(Long id, Product updatedProduct) {
        return productService.update(id, updatedProduct);
    }

    public void delete(Long id) {
        productService.delete(id);
    }
}

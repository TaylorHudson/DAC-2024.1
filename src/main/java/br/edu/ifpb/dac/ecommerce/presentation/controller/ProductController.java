package br.edu.ifpb.dac.ecommerce.presentation.controller;

import br.edu.ifpb.dac.ecommerce.business.mapper.Mapper;
import br.edu.ifpb.dac.ecommerce.business.service.ProductService;
import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryResponseDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.ProductRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.ProductResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final Mapper<ProductRequestDto, Product> requestToProductMapper;
    private final Mapper<Product, ProductResponseDto> productToResponseMapper;

    @PostMapping
    public ResponseEntity<ProductResponseDto> save(@RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.save(requestToProductMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(productToResponseMapper.map(product));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<Product> entities = productService.getProducts();
        List<ProductResponseDto> response = new ArrayList<>();
        if (entities != null && !entities.isEmpty())
            entities.forEach(entity -> response.add(productToResponseMapper.map(entity)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProductById(@PathVariable Long id) {
        ProductResponseDto response = productToResponseMapper.map(productService.getProductById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> update(@PathVariable Long id, @RequestBody @Valid ProductRequestDto requestDto) {
        Product product = productService.update(id, requestToProductMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(productToResponseMapper.map(product));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

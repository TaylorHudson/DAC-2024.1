package br.edu.ifpb.dac.ecommerce.presentation.controller;

import br.edu.ifpb.dac.ecommerce.business.mapper.Mapper;
import br.edu.ifpb.dac.ecommerce.business.service.ProductService;
import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import br.edu.ifpb.dac.ecommerce.presentation.controller.contract.ProductApiContract;
import br.edu.ifpb.dac.ecommerce.presentation.dto.ProductRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@RequestScope
@RestController
@RequiredArgsConstructor
public class ProductController implements ProductApiContract {

    private final ProductService productService;
    private final Mapper<ProductRequestDto, Product> requestToProductMapper;
    private final Mapper<Product, ProductResponseDto> productToResponseMapper;

    @Override
    public ResponseEntity<ProductResponseDto> save(ProductRequestDto requestDto) {
        Product product = productService.save(requestToProductMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(productToResponseMapper.map(product));
    }

    @Override
    public ResponseEntity<List<ProductResponseDto>> getProducts() {
        List<Product> entities = productService.getProducts();
        List<ProductResponseDto> response = new ArrayList<>();
        if (entities != null && !entities.isEmpty())
            entities.forEach(entity -> response.add(productToResponseMapper.map(entity)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ProductResponseDto> getProductById(Long id) {
        ProductResponseDto response = productToResponseMapper.map(productService.getProductById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<ProductResponseDto> update(Long id, ProductRequestDto requestDto) {
        Product product = productService.update(id, requestToProductMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(productToResponseMapper.map(product));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

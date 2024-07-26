package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityNotFoundException;
import br.edu.ifpb.dac.ecommerce.model.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;
    @InjectMocks
    private ProductServiceImpl productService;

    @Test
    void shouldSaveWithSuccess() {
        String productName = "productName";
        Product product = Product.builder().id(1L).name(productName).build();

        when(productRepository.existsByName(productName)).thenReturn(false);
        when(productRepository.save(product)).thenReturn(product);

        Product newProduct = productService.save(product);

        assertEquals(1L, newProduct.getId());
        assertEquals(productName, newProduct.getName());
        verify(productRepository, times(1)).existsByName(productName);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldTrySaveAndThrowEntityAlreadyExistsException() {
        String productName = "productName";
        Product product = Product.builder().id(1L).name(productName).build();

        when(productRepository.existsByName(productName)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> productService.save(product));
        verify(productRepository, times(1)).existsByName(productName);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void shouldReturnAllProducts() {
        List<Product> products = List.of(
                Product.builder().id(1L).name("product1").build(),
                Product.builder().id(2L).name("product2").build()
        );

        when(productRepository.findAll()).thenReturn(products);

        List<Product> result = productService.getProducts();

        assertEquals(2, result.size());
        assertEquals("product1", result.get(0).getName());
        assertEquals("product2", result.get(1).getName());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnProductById() {
        Long productId = 1L;
        Product product = Product.builder().id(productId).name("productName").build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Product result = productService.getProductById(productId);

        assertEquals(productId, result.getId());
        assertEquals("productName", result.getName());
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenProductByIdNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.getProductById(productId));
        verify(productRepository, times(1)).findById(productId);
    }

    @Test
    void shouldUpdateProductWithSuccess() {
        Product product = Product.builder().id(1L).name("updatedName").build();

        when(productRepository.save(product)).thenReturn(product);

        Product updatedProduct = productService.update(product);

        assertEquals(1L, updatedProduct.getId());
        assertEquals("updatedName", updatedProduct.getName());
        verify(productRepository, times(1)).save(product);
    }

    @Test
    void shouldDeleteProductWithSuccess() {
        Long productId = 1L;
        Product product = Product.builder().id(productId).name("productName").build();

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        productService.delete(productId);

        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, times(1)).delete(product);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeleteProductByIdNotFound() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> productService.delete(productId));
        verify(productRepository, times(1)).findById(productId);
        verify(productRepository, never()).delete(any(Product.class));
    }
}

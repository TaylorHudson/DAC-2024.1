package br.edu.ifpb.dac.ecommerce.business.mapper;

import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import br.edu.ifpb.dac.ecommerce.presentation.dto.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductToResponseMapper implements Mapper<Product, ProductResponseDto> {

    @Override
    public ProductResponseDto map(Product product) {
        return new ProductResponseDto(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getImages(),
                product.getCategory().getId()
        );
    }
}

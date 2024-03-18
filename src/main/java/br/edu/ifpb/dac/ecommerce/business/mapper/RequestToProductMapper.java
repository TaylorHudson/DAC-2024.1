package br.edu.ifpb.dac.ecommerce.business.mapper;

import br.edu.ifpb.dac.ecommerce.business.service.CategoryService;
import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import br.edu.ifpb.dac.ecommerce.presentation.dto.ProductRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RequestToProductMapper implements Mapper<ProductRequestDto, Product> {

    private final CategoryService categoryService;

    @Override
    public Product map(ProductRequestDto input) {
        Category category = categoryService.getCategoryById(input.categoryId());

        return new Product(
                input.name(),
                input.description(),
                input.price(),
                input.images(),
                category
        );
    }
}

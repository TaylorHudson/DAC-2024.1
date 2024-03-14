package br.edu.ifpb.dac.ecommerce.business.mapper;

import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CategoryToResponseMapper implements Mapper<Category, CategoryResponseDto> {

    @Override
    public CategoryResponseDto map(Category category) {
        List<Product> products = category.getRelatedProducts();
        List<Long> ids = new ArrayList<>();
        if (products != null && !products.isEmpty())
            products.forEach(product -> ids.add(product.getId()));

        return new CategoryResponseDto(
                category.getId(),
                category.getName(),
                category.getDescription(),
                ids
        );
    }

}

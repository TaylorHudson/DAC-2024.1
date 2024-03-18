package br.edu.ifpb.dac.ecommerce.business.mapper;

import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryRequestDto;
import org.springframework.stereotype.Component;

@Component
public class RequestToCategoryMapper implements Mapper<CategoryRequestDto, Category> {

    @Override
    public Category map(CategoryRequestDto request) {
        return new Category(
                request.name(),
                request.description()
        );
    }

}

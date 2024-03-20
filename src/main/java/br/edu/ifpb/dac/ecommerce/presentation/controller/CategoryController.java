package br.edu.ifpb.dac.ecommerce.presentation.controller;

import br.edu.ifpb.dac.ecommerce.business.mapper.Mapper;
import br.edu.ifpb.dac.ecommerce.business.service.CategoryService;
import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.presentation.controller.contract.CategoryApiContract;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@RequestScope
@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryApiContract {

    private final CategoryService categoryService;
    private final Mapper<CategoryRequestDto, Category> requestToCategoryMapper;
    private final Mapper<Category, CategoryResponseDto> categoryToResponseMapper;

    @Override
    public ResponseEntity<CategoryResponseDto> save(CategoryRequestDto requestDto) {
        Category category = categoryService.save(requestToCategoryMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryToResponseMapper.map(category));
    }

    @Override
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<Category> entities = categoryService.getCategories();
        List<CategoryResponseDto> response = new ArrayList<>();
        if (entities != null && !entities.isEmpty())
            entities.forEach(entity -> response.add(categoryToResponseMapper.map(entity)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<CategoryResponseDto> getCategoryById(Long id) {
        CategoryResponseDto response = categoryToResponseMapper.map(categoryService.getCategoryById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<CategoryResponseDto> update(Long id, CategoryRequestDto requestDto) {
        Category category = categoryService.update(id, requestToCategoryMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(categoryToResponseMapper.map(category));
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

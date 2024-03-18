package br.edu.ifpb.dac.ecommerce.presentation.controller;

import br.edu.ifpb.dac.ecommerce.business.mapper.Mapper;
import br.edu.ifpb.dac.ecommerce.business.service.CategoryService;
import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.CategoryResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import java.util.ArrayList;
import java.util.List;

@RequestScope
@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
    private final Mapper<CategoryRequestDto, Category> requestToCategoryMapper;
    private final Mapper<Category, CategoryResponseDto> categoryToResponseMapper;

    @PostMapping
    public ResponseEntity<CategoryResponseDto> save(@RequestBody @Valid CategoryRequestDto requestDto) {
        Category category = categoryService.save(requestToCategoryMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryToResponseMapper.map(category));
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponseDto>> getCategories() {
        List<Category> entities = categoryService.getCategories();
        List<CategoryResponseDto> response = new ArrayList<>();
        if (entities != null && !entities.isEmpty())
            entities.forEach(entity -> response.add(categoryToResponseMapper.map(entity)));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> getCategoryById(@PathVariable Long id) {
        CategoryResponseDto response = categoryToResponseMapper.map(categoryService.getCategoryById(id));
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDto> update(@PathVariable Long id, @RequestBody @Valid CategoryRequestDto requestDto) {
        Category category = categoryService.update(id, requestToCategoryMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.OK).body(categoryToResponseMapper.map(category));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

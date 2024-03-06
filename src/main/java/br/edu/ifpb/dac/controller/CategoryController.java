package br.edu.ifpb.dac.controller;

import br.edu.ifpb.dac.model.Category;
import br.edu.ifpb.dac.model.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    public Category save(Category category) {
        return categoryService.save(category);
    }

    public List<Category> getCategories() {
        return categoryService.getCategories();
    }

    public Category getCategoryById(Long id) {
        return categoryService.getCategoryById(id);
    }

    public Category update(Long id, Category updatedCategory) {
        return categoryService.update(id, updatedCategory);
    }

    public void delete(Long id) {
        categoryService.delete(id);
    }
}
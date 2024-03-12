package br.edu.ifpb.dac.model.service;

import br.edu.ifpb.dac.model.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category newCategory);
    List<Category> getCategories();
    Category getCategoryById(Long id);
    Category update(Long id, Category updatedCategory);
    void delete(Long id);
}

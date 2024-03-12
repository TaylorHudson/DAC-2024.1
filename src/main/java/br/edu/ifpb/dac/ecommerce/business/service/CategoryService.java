package br.edu.ifpb.dac.ecommerce.business.service;


import br.edu.ifpb.dac.ecommerce.model.entity.Category;

import java.util.List;

public interface CategoryService {
    Category save(Category newCategory);
    List<Category> getCategories();
    Category getCategoryById(Long id);
    Category update(Long id, Category updatedCategory);
    void delete(Long id);
}

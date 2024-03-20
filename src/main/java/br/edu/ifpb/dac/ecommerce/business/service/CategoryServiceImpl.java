package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.model.repository.CategoryRepository;
import br.edu.ifpb.dac.ecommerce.model.repository.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.ecommerce.model.repository.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category save(Category newCategory) {
        if (categoryRepository.existsByName(newCategory.getName()))
            throw new EntityAlreadyExistsException();

        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Category update(Long id, Category updatedCategory) {
        categoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        if (categoryRepository.existsByName(updatedCategory.getName()))
            throw new EntityAlreadyExistsException();

        updatedCategory.setId(id);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        var product = categoryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        categoryRepository.delete(product);
    }
}

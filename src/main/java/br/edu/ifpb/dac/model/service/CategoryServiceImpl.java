package br.edu.ifpb.dac.model.service;

import br.edu.ifpb.dac.model.Category;
import br.edu.ifpb.dac.model.repository.CategoryRepository;
import br.edu.ifpb.dac.model.repository.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.model.repository.exception.EntityNotFoundException;
import br.edu.ifpb.dac.model.validator.CategoryValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryValidator categoryValidator;

    @Override
    public Category save(Category newCategory) {
        categoryValidator.validate(newCategory);
        if (categoryRepository.existsByName(newCategory.getName()))
            throw new EntityAlreadyExistsException("Entity with the name supplied already exists");

        return categoryRepository.save(newCategory);
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id));
    }

    @Override
    public Category update(Long id, Category updatedCategory) {
        categoryValidator.validate(updatedCategory);
        if (categoryRepository.existsByName(updatedCategory.getName()))
            throw new EntityAlreadyExistsException("Entity with the name supplied already exists");

        updatedCategory.setId(id);
        return categoryRepository.save(updatedCategory);
    }

    @Override
    public void delete(Long id) {
        var product = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Entity not found: " + id));
        categoryRepository.delete(product);
    }
}

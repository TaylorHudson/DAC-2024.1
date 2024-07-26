package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Category;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityNotFoundException;
import br.edu.ifpb.dac.ecommerce.model.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {

    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    void shouldSaveWithSuccess() {
        String categoryName = "categoryName";
        Category category = Category.builder().id(1L).name(categoryName).build();

        when(categoryRepository.existsByName(categoryName)).thenReturn(false);
        when(categoryRepository.save(category)).thenReturn(category);

        Category newCategory = categoryService.save(category);

        assertEquals(1L, newCategory.getId());
        assertEquals(categoryName, newCategory.getName());
        verify(categoryRepository, times(1)).existsByName(categoryName);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldTrySaveAndThrowsEntityAlreadyExistsException() {
        String categoryName = "categoryName";
        Category category = Category.builder().id(1L).name(categoryName).build();

        when(categoryRepository.existsByName(categoryName)).thenReturn(true);

        assertThrows(EntityAlreadyExistsException.class, () -> categoryService.save(category));
        verify(categoryRepository, times(1)).existsByName(categoryName);
        verify(categoryRepository, never()).save(any(Category.class));
    }

    @Test
    void shouldReturnAllCategories() {
        List<Category> categories = List.of(
                Category.builder().id(1L).name("category1").build(),
                Category.builder().id(2L).name("category2").build()
        );

        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> result = categoryService.getCategories();

        assertEquals(2, result.size());
        assertEquals("category1", result.get(0).getName());
        assertEquals("category2", result.get(1).getName());
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnCategoryById() {
        Long categoryId = 1L;
        Category category = Category.builder().id(categoryId).name("categoryName").build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        Category result = categoryService.getCategoryById(categoryId);

        assertEquals(categoryId, result.getId());
        assertEquals("categoryName", result.getName());
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCategoryByIdNotFound() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getCategoryById(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    void shouldUpdateCategoryWithSuccess() {
        Category category = Category.builder().id(1L).name("updatedName").build();

        when(categoryRepository.save(category)).thenReturn(category);

        Category updatedCategory = categoryService.update(category);

        assertEquals(1L, updatedCategory.getId());
        assertEquals("updatedName", updatedCategory.getName());
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    void shouldDeleteCategoryWithSuccess() {
        Long categoryId = 1L;
        Category category = Category.builder().id(categoryId).name("categoryName").build();

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.delete(categoryId);

        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, times(1)).delete(category);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeleteCategoryByIdNotFound() {
        Long categoryId = 1L;

        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.delete(categoryId));
        verify(categoryRepository, times(1)).findById(categoryId);
        verify(categoryRepository, never()).delete(any(Category.class));
    }
}

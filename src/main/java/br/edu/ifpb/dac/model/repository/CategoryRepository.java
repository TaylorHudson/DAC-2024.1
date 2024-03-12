package br.edu.ifpb.dac.model.repository;

import br.edu.ifpb.dac.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    boolean existsByName(String name);
}

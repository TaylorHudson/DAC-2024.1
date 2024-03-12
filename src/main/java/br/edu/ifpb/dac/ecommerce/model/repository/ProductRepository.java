package br.edu.ifpb.dac.ecommerce.model.repository;

import br.edu.ifpb.dac.ecommerce.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsByName(String name);
}

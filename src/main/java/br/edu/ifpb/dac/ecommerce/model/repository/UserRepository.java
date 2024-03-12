package br.edu.ifpb.dac.ecommerce.model.repository;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

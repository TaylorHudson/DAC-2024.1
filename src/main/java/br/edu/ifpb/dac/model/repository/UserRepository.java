package br.edu.ifpb.dac.model.repository;

import br.edu.ifpb.dac.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

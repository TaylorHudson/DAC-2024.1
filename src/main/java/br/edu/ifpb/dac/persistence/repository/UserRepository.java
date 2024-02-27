package br.edu.ifpb.dac.persistence.repository;

import br.edu.ifpb.dac.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}

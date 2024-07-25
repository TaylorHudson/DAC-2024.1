package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    User save(User user);
    User update(User user);
    void delete(Long id);
    User findById(Long id);
    User findByEmail(String email);
    User findByUsername(String username);
    List<User> findAll();
}

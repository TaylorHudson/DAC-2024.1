package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityNotFoundException;
import br.edu.ifpb.dac.ecommerce.model.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoderService passwordEncoderService;

    @Override
    public User save(User user) {
        if (user.getId() != null) {
            throw new EntityAlreadyExistsException();
        }

        passwordEncoderService.encodePassword(user);
        user.setRoles(List.of(roleService.findDefault()));
        return userRepository.save(user);
    }

    @Override
    public User update(User updatedUser) {
        return userRepository.save(updatedUser);
    }

    @Override
    public void delete(Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Ops! Não foi encontrado nenhum usuário com as credenciais passadas"));
    }
}

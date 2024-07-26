package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Role;
import br.edu.ifpb.dac.ecommerce.model.entity.User;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityAlreadyExistsException;
import br.edu.ifpb.dac.ecommerce.model.exception.EntityNotFoundException;
import br.edu.ifpb.dac.ecommerce.model.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoderService passwordEncoderService;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void shouldSaveUserWithSuccess() {
        User user = User.builder().id(null).username("testUser").email("test@example.com").build();
        Role defaultRole = Role.builder().name("USER").build();

        when(roleService.findDefault()).thenReturn(defaultRole);
        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.save(user);

        assertNotNull(savedUser);
        assertEquals("testUser", savedUser.getUsername());
        assertEquals("test@example.com", savedUser.getEmail());
        verify(passwordEncoderService, times(1)).encodePassword(user);
        verify(roleService, times(1)).findDefault();
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void shouldThrowEntityAlreadyExistsExceptionWhenSavingUserWithId() {
        User user = User.builder().id(1L).username("testUser").email("test@example.com").build();

        assertThrows(EntityAlreadyExistsException.class, () -> userService.save(user));
        verify(passwordEncoderService, never()).encodePassword(any(User.class));
        verify(roleService, never()).findDefault();
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void shouldUpdateUserWithSuccess() {
        User updatedUser = User.builder().id(1L).username("updatedUser").email("updated@example.com").build();

        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        User result = userService.update(updatedUser);

        assertNotNull(result);
        assertEquals("updatedUser", result.getUsername());
        assertEquals("updated@example.com", result.getEmail());
        verify(userRepository, times(1)).save(updatedUser);
    }

    @Test
    void shouldDeleteUserWithSuccess() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("testUser").email("test@example.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.delete(userId);

        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenDeletingUserNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.delete(userId));
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, never()).delete(any(User.class));
    }

    @Test
    void shouldFindUserByIdWithSuccess() {
        Long userId = 1L;
        User user = User.builder().id(userId).username("testUser").email("test@example.com").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User foundUser = userService.findById(userId);

        assertNotNull(foundUser);
        assertEquals(userId, foundUser.getId());
        assertEquals("testUser", foundUser.getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindingUserByIdNotFound() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findById(userId));
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void shouldFindUserByEmailWithSuccess() {
        String email = "test@example.com";
        User user = User.builder().id(1L).username("testUser").email(email).build();

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail(email);

        assertNotNull(foundUser);
        assertEquals(email, foundUser.getEmail());
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindingUserByEmailNotFound() {
        String email = "test@example.com";

        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByEmail(email));
        verify(userRepository, times(1)).findByEmail(email);
    }

    @Test
    void shouldFindUserByUsernameWithSuccess() {
        String username = "testUser";
        User user = User.builder().id(1L).username(username).email("test@example.com").build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User foundUser = userService.findByUsername(username);

        assertNotNull(foundUser);
        assertEquals(username, foundUser.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenFindingUserByUsernameNotFound() {
        String username = "testUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.findByUsername(username));
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void shouldFindAllUsersWithSuccess() {
        List<User> users = List.of(
                User.builder().id(1L).username("user1").email("user1@example.com").build(),
                User.builder().id(2L).username("user2").email("user2@example.com").build()
        );

        when(userRepository.findAll()).thenReturn(users);

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
        assertEquals("user1", result.get(0).getUsername());
        assertEquals("user2", result.get(1).getUsername());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void shouldLoadUserByUsernameWithSuccess() {
        String username = "test";
        User user = User.builder().id(1L).username(username).build();

        when(userRepository.findByEmail(username)).thenReturn(Optional.of(user));

        UserDetails userDetails = userService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(username, userDetails.getUsername());
        verify(userRepository, times(1)).findByEmail(username);
    }

    @Test
    void shouldThrowUsernameNotFoundExceptionWhenLoadingUserByUsernameNotFound() {
        String username = "test@example.com";

        when(userRepository.findByEmail(username)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername(username));
        verify(userRepository, times(1)).findByEmail(username);
    }
}

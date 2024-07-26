package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Role;
import br.edu.ifpb.dac.ecommerce.model.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    void shouldCreateAvailableRolesOnStartup() {
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.empty());
        when(roleRepository.findByName("USER")).thenReturn(Optional.empty());

        roleService.createAvailableRoles();

        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(roleRepository, times(1)).findByName("USER");

        verify(roleRepository, times(1)).save(argThat(role -> "ADMIN".equals(role.getName())));
        verify(roleRepository, times(1)).save(argThat(role -> "USER".equals(role.getName())));
    }

    @Test
    void shouldNotCreateRoleIfAlreadyExists() {
        Role existingUserRole = new Role("USER");
        Role existingAdminRole = new Role("USER");

        when(roleRepository.findByName("USER")).thenReturn(Optional.of(existingUserRole));
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(existingAdminRole));

        roleService.createAvailableRoles();

        verify(roleRepository, times(1)).findByName("USER");
        verify(roleRepository, times(1)).findByName("ADMIN");
        verify(roleRepository, never()).save(any(Role.class));
    }

    @Test
    void shouldFindRoleByNameSuccessfully() {
        Role role = new Role("ADMIN");
        when(roleRepository.findByName("ADMIN")).thenReturn(Optional.of(role));

        Role foundRole = roleService.findByName("ADMIN");

        assertNotNull(foundRole);
        assertEquals("ADMIN", foundRole.getName());
        verify(roleRepository, times(1)).findByName("ADMIN");
    }

    @Test
    void shouldReturnNullIfRoleNotFoundByName() {
        when(roleRepository.findByName("NON_EXISTENT_ROLE")).thenReturn(Optional.empty());

        Role role = roleService.findByName("NON_EXISTENT_ROLE");

        assertNull(role);
        verify(roleRepository, times(1)).findByName("NON_EXISTENT_ROLE");
    }

    @Test
    void shouldFindDefaultRole() {
        Role defaultRole = new Role("USER");
        when(roleRepository.findByName("USER")).thenReturn(Optional.of(defaultRole));

        Role role = roleService.findDefault();

        assertNotNull(role);
        assertEquals("USER", role.getName());
        verify(roleRepository, times(1)).findByName("USER");
    }
}

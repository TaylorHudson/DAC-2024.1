package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Role;
import br.edu.ifpb.dac.ecommerce.model.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void createAvailableRoles() {
        log.info("Creating available roles");
        var roles = Arrays.asList(AVAILABLE_ROLES.values());
        roles.forEach(role -> {
            var roleOptional = roleRepository.findByName(role.name());

            if (roleOptional.isEmpty()) {
                roleRepository.save(new Role(role.name()));
            }

        });
    }

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }

    @Override
    public Role findDefault() {
        return findByName(AVAILABLE_ROLES.USER.name());
    }


}

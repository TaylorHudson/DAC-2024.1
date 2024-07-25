package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.Role;

public interface RoleService {
    enum AVAILABLE_ROLES { ADMIN, USER }

    Role findByName(String name);
    Role findDefault();
}

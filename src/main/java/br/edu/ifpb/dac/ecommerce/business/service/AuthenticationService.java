package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;

public interface AuthenticationService {
    String login(String email, String password);
    User getLoggedUser();
}

package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface PasswordEncoderService extends PasswordEncoder {
    void encodePassword(User user);
}

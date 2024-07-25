package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordEncoderServiceImpl extends BCryptPasswordEncoder implements PasswordEncoderService {

    @Override
    public void encodePassword(User user) {
        String encodedPassword = encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

}

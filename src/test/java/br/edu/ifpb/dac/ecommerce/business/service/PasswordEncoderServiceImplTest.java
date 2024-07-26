package br.edu.ifpb.dac.ecommerce.business.service;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
class PasswordEncoderServiceImplTest {

    @Spy
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private PasswordEncoderServiceImpl passwordEncoderService;

    @Test
    void shouldEncodePasswordSuccessfully() {
        String plainPassword = "plainPassword";
        User user = User.builder().password(plainPassword).build();

        passwordEncoderService.encodePassword(user);

        assertNotEquals(plainPassword, user.getPassword());
        assertTrue(bCryptPasswordEncoder.matches(plainPassword, user.getPassword()));
    }
}

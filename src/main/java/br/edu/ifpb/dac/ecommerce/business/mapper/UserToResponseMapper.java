package br.edu.ifpb.dac.ecommerce.business.mapper;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import br.edu.ifpb.dac.ecommerce.presentation.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserToResponseMapper implements Mapper<User, UserResponseDto> {

    @Override
    public UserResponseDto map(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }

}

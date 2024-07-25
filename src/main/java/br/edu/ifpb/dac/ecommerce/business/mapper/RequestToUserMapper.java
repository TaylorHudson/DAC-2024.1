package br.edu.ifpb.dac.ecommerce.business.mapper;

import br.edu.ifpb.dac.ecommerce.model.entity.User;
import br.edu.ifpb.dac.ecommerce.presentation.dto.UserRequestDto;
import org.springframework.stereotype.Component;

@Component
public class RequestToUserMapper implements Mapper<UserRequestDto, User> {

    @Override
    public User map(UserRequestDto request) {
        return User.builder()
                .id(request.id())
                .username(request.username())
                .email(request.email())
                .password(request.password())
                .document(request.document())
                .build();
    }
}

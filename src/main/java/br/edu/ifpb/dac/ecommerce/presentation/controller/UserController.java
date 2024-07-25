package br.edu.ifpb.dac.ecommerce.presentation.controller;

import br.edu.ifpb.dac.ecommerce.business.mapper.Mapper;
import br.edu.ifpb.dac.ecommerce.business.service.UserService;
import br.edu.ifpb.dac.ecommerce.model.entity.User;
import br.edu.ifpb.dac.ecommerce.presentation.controller.contract.UserApiContract;
import br.edu.ifpb.dac.ecommerce.presentation.dto.UserRequestDto;
import br.edu.ifpb.dac.ecommerce.presentation.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

import java.util.List;

@RequestScope
@RestController
@RequiredArgsConstructor
public class UserController implements UserApiContract {

    private final UserService userService;
    private final Mapper<UserRequestDto, User> requestToUserMapper;
    private final Mapper<User, UserResponseDto> userToResponseMapper;

    @Override
    public ResponseEntity<UserResponseDto> save(UserRequestDto requestDto) {
        User user = userService.save(requestToUserMapper.map(requestDto));
        return ResponseEntity.status(HttpStatus.CREATED).body(userToResponseMapper.map(user));
    }

    @Override
    public ResponseEntity<List<UserResponseDto>> getUsers() {
        var users = userService.findAll();
        var response = users.stream().map(userToResponseMapper::map).toList();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Override
    public ResponseEntity<UserResponseDto> getUserById(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<UserResponseDto> update(UserRequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseEntity<Void> delete(Long id) {
        return null;
    }
}

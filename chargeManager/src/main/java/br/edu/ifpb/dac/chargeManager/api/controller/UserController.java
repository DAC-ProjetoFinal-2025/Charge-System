package br.edu.ifpb.dac.chargeManager.api.controller;

import br.edu.ifpb.dac.chargeManager.api.dto.CreateUserRequestDto;
import br.edu.ifpb.dac.chargeManager.api.dto.UserResponseDto;
import br.edu.ifpb.dac.chargeManager.business.model.User;
import br.edu.ifpb.dac.chargeManager.business.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody CreateUserRequestDto requestDto) {
        User user = User.builder()
                .name(requestDto.getName())
                .email(requestDto.getEmail())
                .build();

        User createdUser = userService.createUser(user);

        UserResponseDto responseDto = convertToDto(createdUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<User> users = userService.getAllUsers();

        List<UserResponseDto> responseDtos = users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responseDtos);
    }

    private UserResponseDto convertToDto(User user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }
}

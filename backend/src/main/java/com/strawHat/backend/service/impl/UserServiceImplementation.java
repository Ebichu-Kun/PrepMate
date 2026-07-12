package com.strawHat.backend.service.impl;

import com.strawHat.backend.dto.RegisterRequestDto;
import com.strawHat.backend.dto.UserResponseDto;
import com.strawHat.backend.entity.User;
import com.strawHat.backend.repository.UserRepository;
import com.strawHat.backend.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;

    public UserServiceImplementation(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponseDto register(RegisterRequestDto request) {

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);

        UserResponseDto response = new UserResponseDto();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }
}

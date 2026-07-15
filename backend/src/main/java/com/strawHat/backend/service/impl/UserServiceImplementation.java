package com.strawHat.backend.service.impl;

import com.strawHat.backend.dto.LoginRequestDto;
import com.strawHat.backend.dto.LoginResponseDto;
import com.strawHat.backend.dto.RegisterRequestDto;
import com.strawHat.backend.dto.UserResponseDto;
import com.strawHat.backend.entity.User;
import com.strawHat.backend.exception.EmailAlreadyExistException;
import com.strawHat.backend.exception.InvalidCredentialsException;
import com.strawHat.backend.repository.UserRepository;
import com.strawHat.backend.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImplementation implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public UserServiceImplementation(UserRepository userRepository , PasswordEncoder passwordEncoder)
    {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }



    @Override
    public UserResponseDto register(RegisterRequestDto request) {

        if(userRepository.existsByEmail(request.getEmail()))
        {
            throw new EmailAlreadyExistException("Email already exists");
        }

        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        UserResponseDto response = new UserResponseDto();

        response.setId(savedUser.getId());
        response.setName(savedUser.getName());
        response.setEmail(savedUser.getEmail());

        return response;
    }

    @Override
    public LoginResponseDto login(LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() ->
                        new InvalidCredentialsException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return new LoginResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail()
        );
    }
}

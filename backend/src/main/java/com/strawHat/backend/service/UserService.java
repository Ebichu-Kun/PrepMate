package com.strawHat.backend.service;

import com.strawHat.backend.dto.LoginRequestDto;
import com.strawHat.backend.dto.LoginResponseDto;
import com.strawHat.backend.dto.RegisterRequestDto;
import com.strawHat.backend.dto.UserResponseDto;

/** Business operations for user registration and authentication. */
public interface UserService {

    /** Registers a new user account. */
    UserResponseDto register(RegisterRequestDto request);

    /** Authenticates a user and issues a JWT on success. */
    LoginResponseDto login(LoginRequestDto request);
}

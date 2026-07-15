package com.strawHat.backend.service;

import com.strawHat.backend.dto.LoginRequestDto;
import com.strawHat.backend.dto.LoginResponseDto;
import com.strawHat.backend.dto.RegisterRequestDto;
import com.strawHat.backend.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(RegisterRequestDto request);
    LoginResponseDto login(LoginRequestDto request);
}

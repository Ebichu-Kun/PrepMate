package com.strawHat.backend.service;

import com.strawHat.backend.dto.RegisterRequestDto;
import com.strawHat.backend.dto.UserResponseDto;

public interface UserService {
    UserResponseDto register(RegisterRequestDto request);
}

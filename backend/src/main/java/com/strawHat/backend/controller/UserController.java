package com.strawHat.backend.controller;

import com.strawHat.backend.dto.*;
import com.strawHat.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Handles user registration and login. */
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** Registers a new user account. Publicly accessible. */
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(
            @Valid @RequestBody RegisterRequestDto request) {

        UserResponseDto response = userService.register(request);

        ApiResponse<UserResponseDto> apiResponse =
                new ApiResponse<>(true, "User registered successfully", response);

        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /** Authenticates a user and returns a JWT. Publicly accessible. */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponseDto>> login(
            @Valid @RequestBody LoginRequestDto request) {

        LoginResponseDto response = userService.login(request);

        ApiResponse<LoginResponseDto> apiResponse =
                new ApiResponse<>(true, "Login Successful", response);

        return ResponseEntity.ok(apiResponse);
    }
}

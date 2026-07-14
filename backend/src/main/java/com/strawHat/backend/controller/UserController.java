package com.strawHat.backend.controller;

import com.strawHat.backend.dto.ApiResponse;
import com.strawHat.backend.dto.RegisterRequestDto;
import com.strawHat.backend.dto.UserResponseDto;
import com.strawHat.backend.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserResponseDto>> register(@Valid @RequestBody RegisterRequestDto request) {

        System.out.println("Controller hit");

        UserResponseDto response = userService.register(request);

        ApiResponse<UserResponseDto> apiResponse =
                new ApiResponse<>(
                        true,
                        "User registered successfully",
                        response
                );

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(apiResponse);
    }
}

package com.speakly.api.auth.controller;

import com.speakly.api.auth.dto.LoginRequest;
import com.speakly.api.auth.dto.LoginResponse;
import com.speakly.api.auth.service.AuthService;
import com.speakly.api.common.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success(response, "登录成功");
    }
}

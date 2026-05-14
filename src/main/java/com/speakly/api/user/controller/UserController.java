package com.speakly.api.user.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.common.PageResponse;
import com.speakly.api.user.dto.UserInfoResponse;
import com.speakly.api.user.dto.UserListItemResponse;
import com.speakly.api.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/info")
    public ApiResponse<UserInfoResponse> getUserInfo() {
        return ApiResponse.success(userService.getUserInfo());
    }

    @GetMapping("/list")
    public ApiResponse<PageResponse<UserListItemResponse>> getUserList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "20") Integer size,
            @RequestParam(required = false) String status
    ) {
        return ApiResponse.success(userService.getUserList(current, size, status));
    }
}

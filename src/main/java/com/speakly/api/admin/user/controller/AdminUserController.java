package com.speakly.api.admin.user.controller;


import com.speakly.api.admin.user.dto.UserInfoResponse;
import com.speakly.api.admin.user.dto.UserListItemResponse;
import com.speakly.api.admin.user.service.UserService;
import com.speakly.api.common.response.ApiResponse;
import com.speakly.api.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class AdminUserController {

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

package com.speakly.api.admin.user.controller;


import com.speakly.api.admin.user.dto.UserInfoResponse;
import com.speakly.api.admin.user.dto.UserListItemResponse;
import com.speakly.api.admin.user.dto.UserRoleSaveRequest;
import com.speakly.api.admin.user.dto.UserSaveRequest;
import com.speakly.api.admin.user.service.UserService;
import com.speakly.api.common.response.ApiResponse;
import com.speakly.api.common.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/{userId}/roles")
    public ApiResponse<List<Long>> getUserRoles(@PathVariable Long userId) {
        return ApiResponse.success(userService.getUserRoleIds(userId));
    }

    @PostMapping("/roles/save")
    public ApiResponse<Void> saveUserRoles(@Valid @RequestBody UserRoleSaveRequest request) {
        userService.saveUserRoles(request);
        return ApiResponse.success(null, "用户角色保存成功");
    }

    @PostMapping("/create")
    public ApiResponse<Void> createUser(@Valid @RequestBody UserSaveRequest request) {
        userService.saveUser(request);
        return ApiResponse.success(null, "用户创建成功");
    }

    @PutMapping("/update/{id}")
    public ApiResponse<Void> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserSaveRequest request
    ) {
        request.setId(id);
        userService.saveUser(request);
        return ApiResponse.success(null, "用户更新成功");
    }
}

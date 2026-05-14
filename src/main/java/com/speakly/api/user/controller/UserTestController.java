package com.speakly.api.user.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.entity.AdminUser;
import com.speakly.api.repository.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class UserTestController {

    private final AdminUserRepository adminUserRepository;

    @GetMapping("/api/test/users")
    public ApiResponse<List<AdminUser>> getUsers() {

        List<AdminUser> users = adminUserRepository.findAll();
        System.out.println("admin_user count = " + users.size());
        System.out.println("admin_user data = " + users);

        return ApiResponse.success(users);
    }
}

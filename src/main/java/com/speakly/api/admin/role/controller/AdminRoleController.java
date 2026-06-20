package com.speakly.api.admin.role.controller;


import com.speakly.api.admin.role.dto.RoleListItemResponse;
import com.speakly.api.admin.role.service.RoleService;
import com.speakly.api.common.response.ApiResponse;
import com.speakly.api.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class AdminRoleController {

    private final RoleService roleService;

    @GetMapping("/list")
    public ApiResponse<PageResponse<RoleListItemResponse>> getRoleList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        return ApiResponse.success(roleService.getRoleList(current, size));
    }

}

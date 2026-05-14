package com.speakly.api.role.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.common.PageResponse;
import com.speakly.api.role.dto.RoleListItemResponse;
import com.speakly.api.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/list")
    public ApiResponse<PageResponse<RoleListItemResponse>> getRoleList(
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "20") Integer size
    ) {
        return ApiResponse.success(roleService.getRoleList(current, size));
    }

}

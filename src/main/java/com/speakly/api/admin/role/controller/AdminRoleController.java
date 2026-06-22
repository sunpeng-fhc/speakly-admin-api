package com.speakly.api.admin.role.controller;


import com.speakly.api.admin.role.dto.RoleListItemResponse;
import com.speakly.api.admin.role.dto.RoleMenuSaveRequest;
import com.speakly.api.admin.role.dto.RolePermissionSaveRequest;
import com.speakly.api.admin.role.service.RoleService;
import com.speakly.api.common.response.ApiResponse;
import com.speakly.api.common.response.PageResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping("/{roleId}/menus")
    public ApiResponse<List<Long>> getRoleMenus(@PathVariable Long roleId) {
        return ApiResponse.success(roleService.getRoleMenuIds(roleId));
    }

    @PostMapping("/menus/save")
    public ApiResponse<Void> saveRoleMenus(@Valid @RequestBody RoleMenuSaveRequest request) {
        roleService.saveRoleMenus(request);
        return ApiResponse.success(null, "角色菜单权限保存成功");
    }

    @PostMapping("/permissions/save")
    public ApiResponse<Void> saveRolePermissions(@Valid @RequestBody RolePermissionSaveRequest request) {
        roleService.saveRolePermissions(request);
        return ApiResponse.success(null, "角色权限保存成功");
    }
}

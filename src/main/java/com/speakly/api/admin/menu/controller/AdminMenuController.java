package com.speakly.api.admin.menu.controller;


import com.speakly.api.admin.menu.dto.MenuResponse;
import com.speakly.api.admin.menu.service.AdminMenuService;
import com.speakly.api.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v3/system/menus")
@RequiredArgsConstructor
public class AdminMenuController {

    private final AdminMenuService adminMenuService;

    @GetMapping("/simple")
    public ApiResponse<List<MenuResponse>> getSimpleMenus() {
        return ApiResponse.success(adminMenuService.getSimpleMenus());
    }
}
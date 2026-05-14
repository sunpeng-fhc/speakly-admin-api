package com.speakly.api.menu.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.menu.dto.MenuResponse;
import com.speakly.api.menu.service.AdminMenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
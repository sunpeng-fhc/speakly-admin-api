package com.speakly.api.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.portal.service.HomePortalService;
import com.speakly.api.portal.dto.HomePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/home")
@RequiredArgsConstructor
public class HomePortalController {

    private final HomePortalService homeService;

    @GetMapping
    public ApiResponse<HomePageResponse> getHomePage() {
        return ApiResponse.success(homeService.getHomePage());
    }
}

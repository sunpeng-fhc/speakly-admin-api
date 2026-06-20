package com.speakly.api.portal.home.controller;


import com.speakly.api.common.response.ApiResponse;
import com.speakly.api.portal.home.dto.HomePageResponse;
import com.speakly.api.portal.home.service.HomePortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

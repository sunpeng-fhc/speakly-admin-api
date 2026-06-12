package com.speakly.api.controller;

import com.speakly.api.portal.dto.CategoryDetailResponse;
import com.speakly.api.portal.service.CategoryPortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryPortalController {

    private final CategoryPortalService categoryService;

    @GetMapping("/code/{categoryCode}/detail")
    public CategoryDetailResponse getCategoryDetailByCode(@PathVariable String categoryCode) {
        return categoryService.getCategoryDetailByCode(categoryCode);
    }
}

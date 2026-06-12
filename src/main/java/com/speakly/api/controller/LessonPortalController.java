package com.speakly.api.controller;


import com.speakly.api.portal.dto.LessonDetailResponse;
import com.speakly.api.portal.service.LessonPortalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/lessons")
@RequiredArgsConstructor
public class LessonPortalController {

    private final LessonPortalService lessonService;

    @GetMapping("/slug/{slug}")
    public LessonDetailResponse getLessonDetailBySlug(@PathVariable String slug) {
        return lessonService.getLessonDetailBySlug(slug);
    }
}

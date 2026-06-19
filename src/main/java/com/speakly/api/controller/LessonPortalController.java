package com.speakly.api.controller;


import com.speakly.api.common.ApiResponse;
import com.speakly.api.common.PageResponse;
import com.speakly.api.content.lesson.dto.LessonDTO;
import com.speakly.api.content.lesson.dto.LessonQueryDTO;
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

    /**
     * 前台网站：课程列表 / 搜索课程
     *
     * GET /api/lessons
     * GET /api/lessons?keyword=food
     * GET /api/lessons?keyword=food&current=1&size=12
     */
    @GetMapping
    public ApiResponse<PageResponse<LessonDTO>> list(LessonQueryDTO queryDTO) {
        return ApiResponse.success(lessonService.list(queryDTO));
    }


}

package com.speakly.api.portal.lesson.controller;


import com.speakly.api.admin.lesson.dto.LessonDTO;
import com.speakly.api.admin.lesson.dto.LessonQueryDTO;
import com.speakly.api.common.response.ApiResponse;
import com.speakly.api.common.response.PageResponse;
import com.speakly.api.portal.lesson.dto.LessonDetailResponse;
import com.speakly.api.portal.lesson.service.LessonPortalService;
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

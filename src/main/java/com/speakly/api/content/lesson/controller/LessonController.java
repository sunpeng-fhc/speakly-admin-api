package com.speakly.api.content.lesson.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.common.PageResponse;
import com.speakly.api.content.lesson.dto.LessonDTO;
import com.speakly.api.content.lesson.dto.LessonQueryDTO;
import com.speakly.api.content.lesson.service.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lesson")
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @GetMapping("/list")
    public ApiResponse<PageResponse<LessonDTO>> list(LessonQueryDTO queryDTO) {
        return ApiResponse.success(lessonService.list(queryDTO));
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<LessonDTO> detail(@PathVariable Long id) {
        return ApiResponse.success(lessonService.detail(id));
    }

    @PostMapping("/create")
    public ApiResponse<LessonDTO> create(@RequestBody LessonDTO dto) {
        return ApiResponse.success(lessonService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<LessonDTO> update(@PathVariable Long id, @RequestBody LessonDTO dto) {
        return ApiResponse.success(lessonService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        lessonService.delete(id);
        return ApiResponse.success(null, "删除成功");
    }
}

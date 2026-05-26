package com.speakly.api.content.segment.controller;

import com.speakly.api.common.ApiResponse;
import com.speakly.api.common.PageResponse;
import com.speakly.api.content.segment.dto.LessonSegmentDTO;
import com.speakly.api.content.segment.service.LessonSegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson-segment")
@RequiredArgsConstructor
public class LessonSegmentController {

    private final LessonSegmentService segmentService;

    @GetMapping("/list")
    public ApiResponse<PageResponse<LessonSegmentDTO>> list(
            @RequestParam Long lessonId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        return ApiResponse.success(segmentService.list(lessonId, current, size));
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<LessonSegmentDTO> detail(@PathVariable Long id) {
        return ApiResponse.success(segmentService.detail(id));
    }

    @PostMapping("/create")
    public ApiResponse<LessonSegmentDTO> create(@RequestBody LessonSegmentDTO dto) {
        return ApiResponse.success(segmentService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<LessonSegmentDTO> update(@PathVariable Long id, @RequestBody LessonSegmentDTO dto) {
        return ApiResponse.success(segmentService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        segmentService.delete(id);
        return ApiResponse.success(null, "删除成功");
    }

    @GetMapping("/list/{lessonId}")
    public ApiResponse<List<LessonSegmentDTO>> getLessonSegments(
            @PathVariable Long lessonId
    ) {
        return ApiResponse.success(
                segmentService.getByLessonId(lessonId)
        );
    }
}

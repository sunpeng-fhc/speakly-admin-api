package com.speakly.api.content.vocabulary.controller;
import com.speakly.api.common.ApiResponse;
import com.speakly.api.common.PageResponse;
import com.speakly.api.content.vocabulary.dto.LessonVocabularyDTO;
import com.speakly.api.content.vocabulary.service.LessonVocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lesson-vocabulary")
@RequiredArgsConstructor
public class LessonVocabularyController {

    private final LessonVocabularyService vocabularyService;

//    @GetMapping("/list")
//    public ApiResponse<PageResponse<LessonVocabularyDTO>> list(
//            @RequestParam Long lessonId,
//            @RequestParam(defaultValue = "1") Integer current,
//            @RequestParam(defaultValue = "10") Integer size
//    ) {
//        return ApiResponse.success(vocabularyService.list(lessonId, current, size));
//    }

    @GetMapping("/list/{lessonId}")
    public ApiResponse<List<LessonVocabularyDTO>> getLessonVocabularies(
            @PathVariable Long lessonId
    ) {
        return ApiResponse.success(
                vocabularyService.getByLessonId(lessonId)
        );
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<LessonVocabularyDTO> detail(@PathVariable Long id) {
        return ApiResponse.success(vocabularyService.detail(id));
    }

    @PostMapping("/create")
    public ApiResponse<LessonVocabularyDTO> create(@RequestBody LessonVocabularyDTO dto) {
        return ApiResponse.success(vocabularyService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<LessonVocabularyDTO> update(@PathVariable Long id, @RequestBody LessonVocabularyDTO dto) {
        return ApiResponse.success(vocabularyService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        vocabularyService.delete(id);
        return ApiResponse.success(null, "删除成功");
    }

    @PostMapping("/save/{lessonId}")
    public ApiResponse<List<LessonVocabularyDTO>> saveVocabularies(
            @PathVariable Long lessonId,
            @RequestBody List<LessonVocabularyDTO> vocabularies
    ) {
        return ApiResponse.success(
                vocabularyService.saveVocabularies(lessonId, vocabularies),
                "词汇保存成功"
        );
    }
}

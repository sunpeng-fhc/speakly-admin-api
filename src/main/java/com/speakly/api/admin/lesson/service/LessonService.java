package com.speakly.api.admin.lesson.service;


import com.speakly.api.admin.lesson.dto.LessonDTO;
import com.speakly.api.admin.lesson.dto.LessonQueryDTO;
import com.speakly.api.common.response.PageResponse;

public interface LessonService {

    PageResponse<LessonDTO> list(LessonQueryDTO queryDTO);

    LessonDTO detail(Long id);

    LessonDTO create(LessonDTO dto);

    LessonDTO update(Long id, LessonDTO dto);

    void delete(Long id);
}

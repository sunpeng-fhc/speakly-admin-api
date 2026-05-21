package com.speakly.api.content.lesson.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.content.lesson.dto.LessonDTO;
import com.speakly.api.content.lesson.dto.LessonQueryDTO;

public interface LessonService {

    PageResponse<LessonDTO> list(LessonQueryDTO queryDTO);

    LessonDTO detail(Long id);

    LessonDTO create(LessonDTO dto);

    LessonDTO update(Long id, LessonDTO dto);

    void delete(Long id);
}

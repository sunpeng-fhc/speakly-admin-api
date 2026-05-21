package com.speakly.api.content.segment.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.content.segment.dto.LessonSegmentDTO;

public interface LessonSegmentService {

    PageResponse<LessonSegmentDTO> list(Long lessonId, Integer current, Integer size);

    LessonSegmentDTO detail(Long id);

    LessonSegmentDTO create(LessonSegmentDTO dto);

    LessonSegmentDTO update(Long id, LessonSegmentDTO dto);

    void delete(Long id);
}

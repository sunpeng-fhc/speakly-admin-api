package com.speakly.api.content.segment.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.content.segment.dto.LessonSegmentDTO;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonSegmentService {

    PageResponse<LessonSegmentDTO> list(Long lessonId, Integer current, Integer size);

    LessonSegmentDTO detail(Long id);

    LessonSegmentDTO create(LessonSegmentDTO dto);

    LessonSegmentDTO update(Long id, LessonSegmentDTO dto);

    void delete(Long id);

    List<LessonSegmentDTO> getByLessonId(Long lessonId);

    List<LessonSegmentDTO> importSrt(Long lessonId, MultipartFile file);

    List<LessonSegmentDTO> saveSegments(Long lessonId, List<LessonSegmentDTO> segments);
}

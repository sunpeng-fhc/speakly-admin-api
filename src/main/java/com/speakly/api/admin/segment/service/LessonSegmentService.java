package com.speakly.api.admin.segment.service;


import com.speakly.api.admin.segment.dto.LessonSegmentDTO;
import com.speakly.api.common.response.PageResponse;
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

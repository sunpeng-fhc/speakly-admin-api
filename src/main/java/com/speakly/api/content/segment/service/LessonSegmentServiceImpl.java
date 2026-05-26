package com.speakly.api.content.segment.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.entity.Lesson;
import com.speakly.api.content.lesson.repository.LessonRepository;
import com.speakly.api.content.segment.dto.LessonSegmentDTO;
import com.speakly.api.entity.LessonSegment;
import com.speakly.api.content.segment.repository.LessonSegmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonSegmentServiceImpl implements LessonSegmentService{

    private final LessonSegmentRepository segmentRepository;
    private final LessonRepository lessonRepository;

    @Override
    public PageResponse<LessonSegmentDTO> list(Long lessonId, Integer current, Integer size) {
        int pageCurrent = current == null ? 1 : current;
        int pageSize = size == null ? 10 : size;

        Pageable pageable = PageRequest.of(
                pageCurrent - 1,
                pageSize,
                Sort.by(Sort.Direction.ASC, "sortOrder", "id")
        );

        Page<LessonSegment> page = segmentRepository.findByLessonId(lessonId, pageable);

        List<LessonSegmentDTO> records = page.getContent()
                .stream()
                .map(this::toDTO)
                .toList();

        return new PageResponse<>(records, pageCurrent, pageSize, page.getTotalElements());
    }
    @Override
    public LessonSegmentDTO detail(Long id) {
        return toDTO(getById(id));
    }

    @Override
    public LessonSegmentDTO create(LessonSegmentDTO dto) {
        LessonSegment segment = new LessonSegment();
        copyToEntity(dto, segment);

        return toDTO(segmentRepository.save(segment));
    }

    @Override
    public LessonSegmentDTO update(Long id, LessonSegmentDTO dto) {
        LessonSegment segment = getById(id);
        copyToEntity(dto, segment);

        return toDTO(segmentRepository.save(segment));
    }

    @Override
    public void delete(Long id) {
        segmentRepository.delete(getById(id));
    }

    private LessonSegment getById(Long id) {
        return segmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson segment not found"));
    }

    private Lesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }


    private void copyToEntity(LessonSegmentDTO dto, LessonSegment segment) {
        segment.setLesson(getLesson(dto.getLessonId()));
        segment.setStartTime(dto.getStartTime());
        segment.setEndTime(dto.getEndTime());
        segment.setSentence(dto.getSentence());
        segment.setTranslation(dto.getTranslation());
        segment.setSortOrder(dto.getSortOrder());
    }

    private LessonSegmentDTO toDTO(LessonSegment segment) {
        LessonSegmentDTO dto = new LessonSegmentDTO();
        dto.setId(segment.getId());
        dto.setLessonId(segment.getLesson().getId());
        dto.setStartTime(segment.getStartTime());
        dto.setEndTime(segment.getEndTime());
        dto.setSentence(segment.getSentence());
        dto.setTranslation(segment.getTranslation());
        dto.setSortOrder(segment.getSortOrder());
        dto.setCreatedAt(segment.getCreatedAt());
        dto.setUpdatedAt(segment.getUpdatedAt());
        return dto;
    }

    @Override
    public List<LessonSegmentDTO> getByLessonId(Long lessonId) {

        return segmentRepository
                .findByLessonIdOrderBySortOrderAsc(lessonId)
                .stream()
                .map(this::toDTO)
                .toList();
    }
}

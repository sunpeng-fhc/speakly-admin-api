package com.speakly.api.admin.segment.repository;

import com.speakly.api.domain.entity.LessonSegment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonSegmentRepository extends JpaRepository<LessonSegment, Long> {

    Page<LessonSegment> findByLessonId(Long lessonId, Pageable pageable);

    List<LessonSegment> findByLessonIdOrderBySortOrderAsc(Long lessonId);

    void deleteByLessonId(Long lessonId);

    List<LessonSegment> findByLessonIdOrderBySortOrderAscStartTimeAsc(Long lessonId);
}
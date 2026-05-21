package com.speakly.api.content.lesson.repository;

import com.speakly.api.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LessonRepository extends JpaRepository<Lesson, Long> {

    boolean existsBySlug(String slug);

    Page<Lesson> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Lesson> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Lesson> findByLevel(String level, Pageable pageable);

    Page<Lesson> findByStatus(Boolean status, Pageable pageable);
}

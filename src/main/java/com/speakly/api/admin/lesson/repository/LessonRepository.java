package com.speakly.api.admin.lesson.repository;

import com.speakly.api.domain.entity.Lesson;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> , JpaSpecificationExecutor<Lesson> {

    boolean existsBySlug(String slug);

    Page<Lesson> findByCategoryId(Long categoryId, Pageable pageable);

    Page<Lesson> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);

    Page<Lesson> findByLevel(String level, Pageable pageable);

    Page<Lesson> findByStatus(Boolean status, Pageable pageable);

    Optional<Lesson> findFirstByStatusTrueAndIsFeaturedTrueOrderBySortOrderAscCreatedAtDesc();

    Optional<Lesson> findFirstByStatusTrueAndIsDailyTrueOrderByDailyDateDescCreatedAtDesc();

    Optional<Lesson> findFirstByStatusTrueOrderByCreatedAtDesc();

    long countByCategoryIdAndStatusTrue(Long categoryId);

    List<Lesson> findTop4ByStatusTrueOrderByCreatedAtDesc();

    List<Lesson> findTop6ByStatusTrueAndIsFeaturedTrueOrderBySortOrderAscCreatedAtDesc();

    Optional<Lesson> findBySlugAndStatusTrue(String slug);

    List<Lesson> findByCategoryIdAndStatusTrueOrderBySortOrderAscCreatedAtDesc(Long categoryId);

    Page<Lesson> findByStatusTrue(Pageable pageable);

    Page<Lesson> findByTitleContainingIgnoreCaseAndStatusTrue(String keyword, Pageable pageable);


}

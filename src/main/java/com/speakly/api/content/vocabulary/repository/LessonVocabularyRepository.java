package com.speakly.api.content.vocabulary.repository;

import com.speakly.api.entity.LessonVocabulary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Arrays;
import java.util.List;

public interface LessonVocabularyRepository  extends JpaRepository<LessonVocabulary, Long> {

    Page<LessonVocabulary> findByLessonId(Long lessonId, Pageable pageable);

    List<LessonVocabulary> findByLessonIdOrderBySortOrderAsc(Long lessonId);

    void deleteByLessonId(Long lessonId);

}
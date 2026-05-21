package com.speakly.api.content.vocabulary.service;
import com.speakly.api.common.PageResponse;
import com.speakly.api.content.vocabulary.dto.LessonVocabularyDTO;

public interface LessonVocabularyService {

    PageResponse<LessonVocabularyDTO> list(Long lessonId, Integer current, Integer size);

    LessonVocabularyDTO detail(Long id);

    LessonVocabularyDTO create(LessonVocabularyDTO dto);

    LessonVocabularyDTO update(Long id, LessonVocabularyDTO dto);

    void delete(Long id);
}

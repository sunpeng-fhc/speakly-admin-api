package com.speakly.api.admin.vocabulary.service;


import com.speakly.api.admin.vocabulary.dto.LessonVocabularyDTO;
import com.speakly.api.common.response.PageResponse;

import java.util.List;

public interface LessonVocabularyService {

    PageResponse<LessonVocabularyDTO> list(Long lessonId, Integer current, Integer size);

    LessonVocabularyDTO detail(Long id);

    LessonVocabularyDTO create(LessonVocabularyDTO dto);

    LessonVocabularyDTO update(Long id, LessonVocabularyDTO dto);

    void delete(Long id);

    List<LessonVocabularyDTO> getByLessonId(Long lessonId);

    List<LessonVocabularyDTO> saveVocabularies(Long lessonId, List<LessonVocabularyDTO> vocabularies);
}

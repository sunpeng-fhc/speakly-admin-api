package com.speakly.api.content.vocabulary.service;
import com.speakly.api.common.PageResponse;
import com.speakly.api.entity.Lesson;
import com.speakly.api.content.lesson.repository.LessonRepository;
import com.speakly.api.content.vocabulary.dto.LessonVocabularyDTO;
import com.speakly.api.entity.LessonVocabulary;
import com.speakly.api.content.vocabulary.repository.LessonVocabularyRepository;
import com.speakly.api.content.vocabulary.service.LessonVocabularyService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonVocabularyServiceImpl implements LessonVocabularyService {

    private final LessonVocabularyRepository vocabularyRepository;
    private final LessonRepository lessonRepository;

    @Override
    public PageResponse<LessonVocabularyDTO> list(Long lessonId, Integer current, Integer size) {
        int pageCurrent = current == null ? 1 : current;
        int pageSize = size == null ? 10 : size;

        Pageable pageable = PageRequest.of(
                pageCurrent - 1,
                pageSize,
                Sort.by(Sort.Direction.ASC, "sortOrder", "id")
        );

        Page<LessonVocabulary> page = vocabularyRepository.findByLessonId(lessonId, pageable);

        List<LessonVocabularyDTO> records = page.getContent()
                .stream()
                .map(this::toDTO)
                .toList();

        return new PageResponse<>(records, pageCurrent, pageSize, page.getTotalElements());
    }

    @Override
    public LessonVocabularyDTO detail(Long id) {
        return toDTO(getById(id));
    }

    @Override
    public LessonVocabularyDTO create(LessonVocabularyDTO dto) {
        LessonVocabulary vocabulary = new LessonVocabulary();
        copyToEntity(dto, vocabulary);

        return toDTO(vocabularyRepository.save(vocabulary));
    }

    @Override
    public LessonVocabularyDTO update(Long id, LessonVocabularyDTO dto) {
        LessonVocabulary vocabulary = getById(id);
        copyToEntity(dto, vocabulary);

        return toDTO(vocabularyRepository.save(vocabulary));
    }

    @Override
    public void delete(Long id) {
        vocabularyRepository.delete(getById(id));
    }

    private LessonVocabulary getById(Long id) {
        return vocabularyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson vocabulary not found"));
    }

    private Lesson getLesson(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    private void copyToEntity(LessonVocabularyDTO dto, LessonVocabulary vocabulary) {
        vocabulary.setLesson(getLesson(dto.getLessonId()));
        vocabulary.setWord(dto.getWord());
        vocabulary.setPhonetic(dto.getPhonetic());
        vocabulary.setPartOfSpeech(dto.getPartOfSpeech());
        vocabulary.setMeaning(dto.getMeaning());
        vocabulary.setSimpleDefinition(dto.getSimpleDefinition());
        vocabulary.setExampleSentence(dto.getExampleSentence());
        vocabulary.setSortOrder(dto.getSortOrder());
    }

    private LessonVocabularyDTO toDTO(LessonVocabulary vocabulary) {
        LessonVocabularyDTO dto = new LessonVocabularyDTO();
        dto.setId(vocabulary.getId());
        dto.setLessonId(vocabulary.getLesson().getId());
        dto.setWord(vocabulary.getWord());
        dto.setPhonetic(vocabulary.getPhonetic());
        dto.setPartOfSpeech(vocabulary.getPartOfSpeech());
        dto.setMeaning(vocabulary.getMeaning());
        dto.setSimpleDefinition(vocabulary.getSimpleDefinition());
        dto.setExampleSentence(vocabulary.getExampleSentence());
        dto.setSortOrder(vocabulary.getSortOrder());
        dto.setCreatedAt(vocabulary.getCreatedAt());
        dto.setUpdatedAt(vocabulary.getUpdatedAt());
        return dto;
    }
}

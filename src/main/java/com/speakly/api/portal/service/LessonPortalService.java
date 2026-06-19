package com.speakly.api.portal.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.content.lesson.dto.LessonDTO;
import com.speakly.api.content.lesson.dto.LessonQueryDTO;
import com.speakly.api.content.lesson.repository.LessonRepository;
import com.speakly.api.content.segment.repository.LessonSegmentRepository;
import com.speakly.api.content.vocabulary.repository.LessonVocabularyRepository;
import com.speakly.api.entity.Lesson;
import com.speakly.api.entity.LessonSegment;
import com.speakly.api.entity.LessonVocabulary;
import com.speakly.api.portal.dto.LessonDetailResponse;
import com.speakly.api.portal.dto.LessonSegmentResponse;
import com.speakly.api.portal.dto.LessonVocabularyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonPortalService {
    private final LessonRepository lessonRepository;
    private final LessonSegmentRepository lessonSegmentRepository;
    private final LessonVocabularyRepository lessonVocabularyRepository;

    private final com.speakly.api.content.lesson.service.LessonService contentLessonService;

    @Transactional(readOnly = true)
    public LessonDetailResponse getLessonDetailBySlug(String slug) {
        Lesson lesson = lessonRepository.findBySlugAndStatusTrue(slug)
                .orElseThrow(() -> new RuntimeException("Lesson not found: " + slug));

        List<LessonSegment> segments =
                lessonSegmentRepository.findByLessonIdOrderBySortOrderAscStartTimeAsc(lesson.getId());

        List<LessonVocabulary> vocabularies =
                lessonVocabularyRepository.findByLessonIdOrderBySortOrderAscIdAsc(lesson.getId());

        return toDetailResponse(lesson, segments, vocabularies);
    }

    @Transactional(readOnly = true)
    public PageResponse<LessonDTO> list(LessonQueryDTO queryDTO) {
        return contentLessonService.list(queryDTO);
    }


    private LessonDetailResponse toDetailResponse(
            Lesson lesson,
            List<LessonSegment> segments,
            List<LessonVocabulary> vocabularies
    ) {
        return LessonDetailResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .slug(lesson.getSlug())
                .summary(lesson.getSummary())
                .coverImage(lesson.getCoverImage())
                .audioUrl(lesson.getAudioUrl())
                .durationSeconds(lesson.getDurationSeconds())
                .level(lesson.getLevel())
                .transcript(lesson.getTranscript())
                .status(lesson.getStatus())
                .isFeatured(lesson.getIsFeatured())
                .isDaily(lesson.getIsDaily())
                .dailyDate(lesson.getDailyDate())
                .sortOrder(lesson.getSortOrder())
                .publishedAt(lesson.getPublishedAt())
                .createdAt(lesson.getCreatedAt())
                .updatedAt(lesson.getUpdatedAt())

                .categoryId(lesson.getCategory().getId())
                .categoryName(lesson.getCategory().getName())

                .segments(toSegmentResponses(segments))
                .vocabularies(toVocabularyResponses(vocabularies))
                .build();
    }

    private List<LessonSegmentResponse> toSegmentResponses(List<LessonSegment> segments) {
        if (segments == null || segments.isEmpty()) {
            return List.of();
        }

        return segments.stream()
                .map(this::toSegmentResponse)
                .toList();
    }

    private LessonSegmentResponse toSegmentResponse(LessonSegment segment) {
        return LessonSegmentResponse.builder()
                .id(segment.getId())
                .sortOrder(segment.getSortOrder())
                .startTime(segment.getStartTime())
                .endTime(segment.getEndTime())
                .sentence(segment.getSentence())
                .translation(segment.getTranslation())
                .build();
    }

    private List<LessonVocabularyResponse> toVocabularyResponses(List<LessonVocabulary> vocabularies) {
        if (vocabularies == null || vocabularies.isEmpty()) {
            return List.of();
        }

        return vocabularies.stream()
                .map(this::toVocabularyResponse)
                .toList();
    }

    private LessonVocabularyResponse toVocabularyResponse(LessonVocabulary vocabulary) {
        return LessonVocabularyResponse.builder()
                .id(vocabulary.getId())
                .sortOrder(vocabulary.getSortOrder())
                .word(vocabulary.getWord())
                .phonetic(vocabulary.getPhonetic())
                .partOfSpeech(vocabulary.getPartOfSpeech())
                .meaning(vocabulary.getMeaning())
                .simpleDefinition(vocabulary.getSimpleDefinition())
                .exampleSentence(vocabulary.getExampleSentence())
                .build();
    }



}

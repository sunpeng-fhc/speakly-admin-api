package com.speakly.api.content.vocabulary.dto;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonVocabularyDTO {
    private Long id;

    private Long lessonId;

    private String word;

    private String phonetic;

    private String partOfSpeech;

    private String meaning;

    private String simpleDefinition;

    private String exampleSentence;

    private Integer sortOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

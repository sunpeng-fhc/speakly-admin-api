package com.speakly.api.portal.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonVocabularyResponse {

    private Long id;
    private Integer sortOrder;

    private String word;
    private String phonetic;
    private String partOfSpeech;

    private String meaning;
    private String simpleDefinition;
    private String exampleSentence;
}

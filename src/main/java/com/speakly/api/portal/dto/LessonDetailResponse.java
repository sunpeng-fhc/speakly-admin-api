package com.speakly.api.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonDetailResponse {

    private Long id;
    private String title;
    private String slug;
    private String summary;

    private String coverImage;
    private String audioUrl;

    private Integer durationSeconds;
    private String level;
    private String transcript;
    private Boolean status;

    private Boolean isFeatured;
    private Boolean isDaily;
    private LocalDate dailyDate;
    private Integer sortOrder;

    private LocalDateTime publishedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    private Long categoryId;
    private String categoryName;

    private List<LessonSegmentResponse> segments;
    private List<LessonVocabularyResponse> vocabularies;
}

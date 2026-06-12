package com.speakly.api.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryLessonResponse {

    private Long id;
    private String title;
    private String slug;
    private String summary;
    private String coverImage;
    private String audioUrl;
    private String level;
    private Integer durationSeconds;

    private Long categoryId;
    private String categoryName;
}

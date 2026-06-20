package com.speakly.api.portal.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeLessonResponse {

    private Long id;
    private String title;
    private String summary;
    private String slug;
    private String level;
    private Integer durationSeconds;
    private String coverImage;

    private String categoryName;
    private String categoryShortName;
    private String categorySlug;
}
package com.speakly.api.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDetailResponse {

    private Long id;
    private String name;
    private String slug;
    private String description;
    private String icon;

    private Integer lessonCount;
    private Integer totalDurationSeconds;

    private CategoryLessonResponse featuredLesson;
    private List<CategoryLessonResponse> lessons;
}

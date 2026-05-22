package com.speakly.api.content.lesson.dto;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LessonDTO {

    private Long id;

    private Long categoryId;

    private String categoryName;

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

    private Integer sortOrder;

    private Boolean isDaily;

    private LocalDate dailyDate;

    private LocalDateTime publishedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

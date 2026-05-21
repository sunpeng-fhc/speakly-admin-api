package com.speakly.api.content.lesson.dto;

import lombok.Data;

@Data
public class LessonQueryDTO {

    private Long categoryId;

    private String keyword;

    private String level;

    private Boolean status;

    private Integer current = 1;

    private Integer size = 10;
}

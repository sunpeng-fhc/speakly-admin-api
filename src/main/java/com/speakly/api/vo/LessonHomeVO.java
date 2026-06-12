package com.speakly.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LessonHomeVO {
    private Long id;
    private String title;
    private String summary;
    private String slug;
    private String level;
    private Integer durationSeconds;
    private String coverImage;
}
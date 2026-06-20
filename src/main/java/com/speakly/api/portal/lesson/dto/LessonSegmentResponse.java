package com.speakly.api.portal.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonSegmentResponse {

    private Long id;
    private Integer sortOrder;

    private BigDecimal startTime;
    private BigDecimal endTime;

    private String sentence;
    private String translation;
}

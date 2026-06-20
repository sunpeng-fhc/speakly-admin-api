package com.speakly.api.admin.segment.dto;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class LessonSegmentDTO {

    private Long id;

    private Long lessonId;

    private BigDecimal startTime;

    private BigDecimal endTime;

    private String sentence;

    private String translation;

    private Integer sortOrder;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

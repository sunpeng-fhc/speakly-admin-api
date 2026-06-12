package com.speakly.api.content.lesson.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LessonQueryDTO {

    /**
     * 课程标题
     */
    private String title;

    /**
     * 课程标识
     */
    private String slug;

    /**
     * 关键字搜索
     */
    private String keyword;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * A1 / A2
     */
    private String level;

    /**
     * 推荐课程
     */
    private Boolean isFeatured;

    /**
     * 每日推荐
     */
    private Boolean isDaily;

    /**
     * 启用状态
     */
    private Boolean status;

    /**
     * 开始时间
     */
    private LocalDateTime startTime;

    /**
     * 结束时间
     */
    private LocalDateTime endTime;

    /**
     * 当前页
     */
    private Integer current = 1;

    /**
     * 每页数量
     */
    private Integer size = 10;
}

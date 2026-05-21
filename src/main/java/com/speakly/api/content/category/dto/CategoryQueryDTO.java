package com.speakly.api.content.category.dto;

import lombok.Data;

@Data
public class CategoryQueryDTO {

    /**
     * 分类名称
     */
    private String name;

    /**
     * 分类slug
     */
    private String slug;

    /**
     * 状态
     */
    private Boolean status;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 当前页
     */
    private Integer current = 1;

    /**
     * 每页数量
     */
    private Integer size = 10;
}
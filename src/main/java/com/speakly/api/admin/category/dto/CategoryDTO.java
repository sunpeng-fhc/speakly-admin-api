package com.speakly.api.admin.category.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CategoryDTO {

    private Long id;

    private String name;

    private String shortName;

    private String slug;

    private String description;

    private String icon;

    private String coverImage;

    private String themeColor;

    private Integer sortOrder;

    private Boolean isFeatured;

    private Boolean status;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
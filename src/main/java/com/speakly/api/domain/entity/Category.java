package com.speakly.api.domain.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "categories")
@Data
// Speakly 分类
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    // DAILY
    @Column(name = "short_name", length = 50)
    private String shortName;

    @Column(nullable = false, unique = true, length = 100)
    private String slug;

    @Column(columnDefinition = "TEXT")
    private String description;

    private String icon;

    @Column(name = "cover_image")
    private String coverImage;

    @Column(name = "theme_color", length = 20)
    private String themeColor;

    @Column(name = "sort_order")
    private Integer sortOrder = 0;

    @Column(name = "is_featured")
    private Boolean isFeatured = false;

    private Boolean status = true;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
}

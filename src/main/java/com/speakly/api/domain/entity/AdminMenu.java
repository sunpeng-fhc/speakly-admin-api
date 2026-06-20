package com.speakly.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_menu")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "path", nullable = false, length = 200)
    private String path;

    @Column(name = "component", length = 200)
    private String component;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "icon", length = 100)
    private String icon;

    @Column(name = "keep_alive")
    private Boolean keepAlive;

    @Column(name = "is_hide")
    private Boolean isHide;

    @Column(name = "is_hide_tab")
    private Boolean isHideTab;

    @Column(name = "is_full_page")
    private Boolean isFullPage;

    @Column(name = "is_first_level")
    private Boolean isFirstLevel;

    @Column(name = "active_path", length = 200)
    private String activePath;

    @Column(name = "link", length = 500)
    private String link;

    @Column(name = "sort_order")
    private Integer sortOrder;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

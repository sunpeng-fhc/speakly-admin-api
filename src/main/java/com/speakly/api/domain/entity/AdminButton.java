package com.speakly.api.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "admin_button")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminButton {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "button_code", nullable = false, unique = true, length = 100)
    private String buttonCode;

    @Column(name = "button_name", nullable = false, length = 100)
    private String buttonName;

    @Column(name = "description")
    private String description;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}

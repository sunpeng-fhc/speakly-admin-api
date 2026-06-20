package com.speakly.api.repository;

import com.speakly.api.domain.entity.AdminButton;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminButtonRepository extends JpaRepository<AdminButton, Long> {

    List<AdminButton> findByEnabledTrue();
}
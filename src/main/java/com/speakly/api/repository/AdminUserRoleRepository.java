package com.speakly.api.repository;

import com.speakly.api.domain.entity.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminUserRoleRepository extends JpaRepository<AdminUserRole, Long> {

    List<AdminUserRole> findByUserId(Long userId);
}
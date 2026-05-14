package com.speakly.api.repository;

import com.speakly.api.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRoleRepository extends JpaRepository<AdminRole, Long> {

    Optional<AdminRole> findByRoleCode(String roleCode);
}
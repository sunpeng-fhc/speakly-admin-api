package com.speakly.api.repository;

import com.speakly.api.domain.entity.AdminRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRoleMenuRepository extends JpaRepository<AdminRoleMenu, Long> {

    List<AdminRoleMenu> findByRoleIdIn(List<Long> roleIds);
}
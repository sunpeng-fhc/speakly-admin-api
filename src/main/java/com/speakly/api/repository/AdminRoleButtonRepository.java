package com.speakly.api.repository;

import com.speakly.api.domain.entity.AdminRoleButton;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AdminRoleButtonRepository extends JpaRepository<AdminRoleButton, Long> {

    List<AdminRoleButton> findByRoleIdIn(List<Long> roleIds);

    List<AdminRoleButton> findByRoleId(Long roleId);

    void deleteByRoleId(Long roleId);

}

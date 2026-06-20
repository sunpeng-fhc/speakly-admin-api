package com.speakly.api.admin.role.service;


import com.speakly.api.repository.AdminRoleRepository;
import com.speakly.api.admin.role.dto.RoleListItemResponse;
import com.speakly.api.common.response.PageResponse;
import com.speakly.api.domain.entity.AdminRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final AdminRoleRepository adminRoleRepository;

    public PageResponse<RoleListItemResponse> getRoleList(Integer current, Integer size) {
        int pageIndex = current == null || current < 1 ? 0 : current - 1;
        int pageSize = size == null || size < 1 ? 20 : size;

        Page<AdminRole> page = adminRoleRepository.findAll(PageRequest.of(pageIndex, pageSize));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<RoleListItemResponse> records = page.getContent().stream()
                .map(role -> new RoleListItemResponse(
                        role.getId(),
                        role.getRoleName(),
                        role.getRoleCode(),
                        role.getDescription(),
                        role.getEnabled(),
                        role.getCreatedAt() == null ? null : role.getCreatedAt().format(formatter)
                ))
                .toList();

        return new PageResponse<>(
                records,
                current == null ? 1 : current,
                pageSize,
                page.getTotalElements()
        );
    }
}

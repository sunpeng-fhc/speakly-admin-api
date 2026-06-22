package com.speakly.api.admin.role.service;


import com.speakly.api.admin.role.dto.RoleMenuSaveRequest;
import com.speakly.api.admin.role.dto.RolePermissionSaveRequest;
import com.speakly.api.domain.entity.AdminButton;
import com.speakly.api.domain.entity.AdminRoleButton;
import com.speakly.api.domain.entity.AdminRoleMenu;
import com.speakly.api.repository.AdminButtonRepository;
import com.speakly.api.repository.AdminRoleButtonRepository;
import com.speakly.api.repository.AdminRoleMenuRepository;
import com.speakly.api.repository.AdminRoleRepository;
import com.speakly.api.admin.role.dto.RoleListItemResponse;
import com.speakly.api.common.response.PageResponse;
import com.speakly.api.domain.entity.AdminRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final AdminRoleRepository adminRoleRepository;

    private final AdminRoleMenuRepository adminRoleMenuRepository;

    private final AdminRoleButtonRepository adminRoleButtonRepository;

    private final AdminButtonRepository adminButtonRepository;

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


    @Transactional
    public List<Long> getRoleMenuIds(Long roleId) {
        return adminRoleMenuRepository.findByRoleId(roleId)
                .stream()
                .map(AdminRoleMenu::getMenuId)
                .toList();
    }

    @Transactional
    public void saveRoleMenus(RoleMenuSaveRequest request) {
        adminRoleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("角色不存在"));

        adminRoleMenuRepository.deleteByRoleId(request.getRoleId());
        adminRoleMenuRepository.flush();

        if (request.getMenuIds() == null || request.getMenuIds().isEmpty()) {
            return;
        }

        List<AdminRoleMenu> roleMenus = request.getMenuIds()
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .map(menuId -> AdminRoleMenu.builder()
                        .roleId(request.getRoleId())
                        .menuId(menuId)
                        .createdAt(LocalDateTime.now())
                        .build())
                .toList();

        adminRoleMenuRepository.saveAll(roleMenus);
    }


    @Transactional
    public void saveRolePermissions(RolePermissionSaveRequest request) {
        adminRoleRepository.findById(request.getRoleId())
                .orElseThrow(() -> new RuntimeException("角色不存在"));

        // 1. 保存菜单权限
        adminRoleMenuRepository.deleteByRoleId(request.getRoleId());
        adminRoleMenuRepository.flush();

        if (request.getMenuIds() != null && !request.getMenuIds().isEmpty()) {
            List<AdminRoleMenu> roleMenus = request.getMenuIds()
                    .stream()
                    .filter(Objects::nonNull)
                    .distinct()
                    .map(menuId -> AdminRoleMenu.builder()
                            .roleId(request.getRoleId())
                            .menuId(menuId)
                            .createdAt(LocalDateTime.now())
                            .build())
                    .toList();

            adminRoleMenuRepository.saveAll(roleMenus);
        }

        // 2. 保存按钮权限
        adminRoleButtonRepository.deleteByRoleId(request.getRoleId());
        adminRoleButtonRepository.flush();

        if (request.getButtonCodes() == null || request.getButtonCodes().isEmpty()) {
            return;
        }

        List<String> buttonCodes = request.getButtonCodes()
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();

        List<AdminButton> buttons = adminButtonRepository.findByButtonCodeInAndEnabledTrue(buttonCodes);

        List<AdminRoleButton> roleButtons = buttons.stream()
                .map(button -> AdminRoleButton.builder()
                        .roleId(request.getRoleId())
                        .buttonId(button.getId())
                        .createdAt(LocalDateTime.now())
                        .build())
                .toList();

        adminRoleButtonRepository.saveAll(roleButtons);
    }
}

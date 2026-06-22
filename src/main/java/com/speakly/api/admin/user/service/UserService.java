package com.speakly.api.admin.user.service;

import com.speakly.api.admin.user.dto.UserRoleSaveRequest;
import com.speakly.api.admin.user.dto.UserSaveRequest;
import com.speakly.api.domain.entity.AdminButton;
import com.speakly.api.domain.entity.AdminRoleButton;
import com.speakly.api.repository.*;
import com.speakly.api.admin.user.dto.UserInfoResponse;
import com.speakly.api.admin.user.dto.UserListItemResponse;
import com.speakly.api.common.response.PageResponse;
import com.speakly.api.domain.entity.AdminUser;
import com.speakly.api.domain.entity.AdminUserRole;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserRoleRepository adminUserRoleRepository;
    private final AdminRoleRepository adminRoleRepository;

    private final AdminRoleButtonRepository adminRoleButtonRepository;
    private final AdminButtonRepository adminButtonRepository;

    public UserInfoResponse getUserInfo() {

        // V1 阶段：暂时写死 Super
        // 后面接 JWT 后，再从 token 中解析 username
        AdminUser user = adminUserRepository.findByUsername("Super")
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        List<AdminUserRole> userRoles = adminUserRoleRepository.findByUserId(user.getId());

        List<Long> roleIds = userRoles.stream()
                .map(AdminUserRole::getRoleId)
                .toList();

        List<String> roles = roleIds.stream()
                .map(roleId -> adminRoleRepository.findById(roleId))
                .filter(optionalRole -> optionalRole.isPresent())
                .map(optionalRole -> optionalRole.get().getRoleCode())
                .toList();

        List<String> buttons = getButtonCodesByRoleIds(roleIds);

        return new UserInfoResponse(
                String.valueOf(user.getId()),
                user.getUsername(),
                roles,
                buttons,
                user.getEmail()
        );
    }

    private List<String> getButtonCodesByRoleIds(List<Long> roleIds) {
        if (roleIds == null || roleIds.isEmpty()) {
            return List.of();
        }

        List<Long> buttonIds = adminRoleButtonRepository.findByRoleIdIn(roleIds)
                .stream()
                .map(AdminRoleButton::getButtonId)
                .distinct()
                .toList();

        if (buttonIds.isEmpty()) {
            return List.of();
        }

        return adminButtonRepository.findByIdInAndEnabledTrue(buttonIds)
                .stream()
                .map(AdminButton::getButtonCode)
                .distinct()
                .toList();
    }

    public PageResponse<UserListItemResponse> getUserList(Integer current, Integer size, String status) {
        int pageIndex = current == null || current < 1 ? 0 : current - 1;
        int pageSize = size == null || size < 1 ? 20 : size;

        Pageable pageable = PageRequest.of(pageIndex, pageSize);

        Page<AdminUser> page = adminUserRepository.findAll(pageable);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        List<UserListItemResponse> records = page.getContent().stream()
                .filter(user -> status == null || status.isBlank() || status.equals(user.getStatus()))
                .map(user -> {
                    List<AdminUserRole> userRoleList = adminUserRoleRepository.findByUserId(user.getId());

                    List<String> roleCodes = userRoleList.stream()
                            .map(userRole -> adminRoleRepository.findById(userRole.getRoleId()))
                            .filter(optionalRole -> optionalRole.isPresent())
                            .map(optionalRole -> optionalRole.get().getRoleCode())
                            .toList();

                    String genderText = user.getGender() != null && user.getGender() == 0 ? "女" : "男";

                    String createTime = user.getCreatedAt() == null
                            ? null
                            : user.getCreatedAt().format(formatter);

                    String updateTime = user.getUpdatedAt() == null
                            ? null
                            : user.getUpdatedAt().format(formatter);

                    return new UserListItemResponse(
                            user.getId(),
                            user.getCreateBy(),
                            createTime,
                            user.getUpdateBy(),
                            updateTime,
                            user.getStatus(),
                            user.getUsername(),
                            genderText,
                            user.getUsername(),
                            user.getMobile(),
                            user.getEmail(),
                            roleCodes
                    );
                })
                .toList();

        return new PageResponse<>(
                records,
                current == null ? 1 : current,
                pageSize,
                page.getTotalElements()
        );
    }


    @Transactional
    public List<Long> getUserRoleIds(Long userId) {
        adminUserRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        return adminUserRoleRepository.findByUserId(userId)
                .stream()
                .map(AdminUserRole::getRoleId)
                .toList();
    }

    @Transactional
    public void saveUserRoles(UserRoleSaveRequest request) {
        adminUserRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        adminUserRoleRepository.deleteByUserId(request.getUserId());
        adminUserRoleRepository.flush();

        if (request.getRoleIds() == null || request.getRoleIds().isEmpty()) {
            return;
        }

        List<AdminUserRole> userRoles = request.getRoleIds()
                .stream()
                .filter(Objects::nonNull)
                .distinct()
                .map(roleId -> AdminUserRole.builder()
                        .userId(request.getUserId())
                        .roleId(roleId)
                        .createdAt(LocalDateTime.now())
                        .build())
                .toList();

        adminUserRoleRepository.saveAll(userRoles);
    }

    @Transactional
    public void saveUser(UserSaveRequest request) {
        AdminUser user;

        if (request.getId() == null) {
            user = new AdminUser();
            user.setUsername(request.getUsername());
            user.setPasswordHash("123456");
            user.setCreatedAt(LocalDateTime.now());
            user.setStatus(request.getStatus() == null ? "1" : request.getStatus());
        } else {
            user = adminUserRepository.findById(request.getId())
                    .orElseThrow(() -> new RuntimeException("用户不存在"));
            user.setUpdateBy("Super");
        }

        user.setMobile(request.getMobile());
        user.setGender(request.getGender());
        user.setEmail(request.getEmail());
        user.setDepartment(request.getDepartment());
        user.setUpdatedAt(LocalDateTime.now());

        AdminUser savedUser = adminUserRepository.save(user);

        UserRoleSaveRequest roleRequest = new UserRoleSaveRequest();
        roleRequest.setUserId(savedUser.getId());
        roleRequest.setRoleIds(request.getRoleIds());

        saveUserRoles(roleRequest);
    }

}
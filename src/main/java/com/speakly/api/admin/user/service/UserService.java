package com.speakly.api.admin.user.service;



import com.speakly.api.repository.AdminRoleRepository;
import com.speakly.api.repository.AdminUserRepository;
import com.speakly.api.repository.AdminUserRoleRepository;
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

import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AdminUserRepository adminUserRepository;
    private final AdminUserRoleRepository adminUserRoleRepository;
    private final AdminRoleRepository adminRoleRepository;

    public UserInfoResponse getUserInfo() {

        // 目前先写死 Super，后面 JWT 做好后再从 token 里解析 username
        AdminUser user = adminUserRepository.findByUsername("Super")
                .orElseThrow(() -> new RuntimeException("用户不存在"));

        List<AdminUserRole> userRoles = adminUserRoleRepository.findByUserId(user.getId());

        List<String> roles = userRoles.stream()
                .map(userRole -> adminRoleRepository.findById(userRole.getRoleId()))
                .filter(optionalRole -> optionalRole.isPresent())
                .map(optionalRole -> optionalRole.get().getRoleCode())
                .toList();

        List<String> buttons = List.of("B_CODE1", "B_CODE2", "B_CODE3");

        return new UserInfoResponse(
                String.valueOf(user.getId()),
                user.getUsername(),
                roles,
                buttons,
                user.getEmail()
        );
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
}
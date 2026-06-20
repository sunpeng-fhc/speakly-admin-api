package com.speakly.api.admin.role.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RoleListItemResponse {

    private Long roleId;

    private String roleName;

    private String roleCode;

    private String description;

    private Boolean enabled;

    private String createTime;
}

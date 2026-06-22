package com.speakly.api.admin.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class UserSaveRequest {

    private Long id;

    @NotBlank(message = "用户名不能为空")
    private String username;

    private String mobile;

    @NotNull(message = "性别不能为空")
    private Short gender;

    private String email;

    private String department;

    private String status;

    private List<Long> roleIds;
}

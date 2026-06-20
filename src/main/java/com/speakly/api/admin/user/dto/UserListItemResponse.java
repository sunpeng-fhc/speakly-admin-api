package com.speakly.api.admin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserListItemResponse {

    private Long id;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String status;

    private String userName;

    private String userGender;

    private String nickName;

    private String userPhone;

    private String userEmail;

    private List<String> userRoles;
}

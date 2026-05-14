package com.speakly.api.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserInfoResponse {

    private String userId;

    private String userName;

    private List<String> roles;

    private List<String> buttons;

    private String email;
}
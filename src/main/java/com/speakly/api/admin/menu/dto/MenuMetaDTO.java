package com.speakly.api.admin.menu.dto;

import lombok.Data;

import java.util.List;

@Data
public class MenuMetaDTO {

    private String title;

    private String icon;

    private Boolean keepAlive;

    private Boolean hideInMenu;

    private Boolean hideInTab;

    private Boolean fullPage;

    private String activePath;

    private String link;

    private Boolean fixedTab;

    private String date;

    private Boolean enabled;

    private List<AuthButtonDTO> authList;
}

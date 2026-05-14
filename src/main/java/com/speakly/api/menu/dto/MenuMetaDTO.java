package com.speakly.api.menu.dto;

import lombok.Data;

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
}

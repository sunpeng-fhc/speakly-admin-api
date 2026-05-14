package com.speakly.api.menu.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuResponse {

    private String name;

    private String path;

    private String component;

    private MenuMetaDTO meta;

    private List<MenuResponse> children;

}

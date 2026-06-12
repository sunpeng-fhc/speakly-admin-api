package com.speakly.api.portal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomeCategoryResponse {

    private Long id;

    private String name;

    private String shortName;

    private String slug;

    private String coverImage;

    private Long lessonCount;
}

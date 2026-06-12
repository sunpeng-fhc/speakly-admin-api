package com.speakly.api.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryHomeVO {

    private Long id;
    private String name;
    private String slug;
    private String coverImage;
    private Long lessonCount;
}

package com.speakly.api.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePageVO {

    private LessonHomeVO heroLesson;
    private List<CategoryHomeVO> categories;
    private List<LessonHomeVO> latestLessons;
}

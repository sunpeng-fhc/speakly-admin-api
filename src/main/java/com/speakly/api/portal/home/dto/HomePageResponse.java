package com.speakly.api.portal.home.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomePageResponse {

    private HomeLessonResponse heroLesson;

    private List<HomeCategoryResponse> categories;

    private List<HomeLessonResponse> recentLessons;

    private List<HomeLessonResponse> featuredLessons;

}
package com.speakly.api.portal.service;

import com.speakly.api.content.category.repository.CategoryRepository;
import com.speakly.api.content.lesson.repository.LessonRepository;
import com.speakly.api.entity.Category;
import com.speakly.api.entity.Lesson;
import com.speakly.api.portal.dto.HomeCategoryResponse;
import com.speakly.api.portal.dto.HomeLessonResponse;
import com.speakly.api.portal.dto.HomePageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class HomePortalService {

    private final LessonRepository lessonRepository;
    private final CategoryRepository categoryRepository;

    public HomePageResponse getHomePage() {
        Lesson heroLesson = lessonRepository
                .findFirstByStatusTrueAndIsDailyTrueOrderByDailyDateDescCreatedAtDesc()
                .or(() -> lessonRepository.findFirstByStatusTrueAndIsFeaturedTrueOrderBySortOrderAscCreatedAtDesc())
                .or(() -> lessonRepository.findFirstByStatusTrueOrderByCreatedAtDesc())
                .orElse(null);

        List<HomeCategoryResponse> categories = categoryRepository
                .findByStatusTrueOrderBySortOrderAscCreatedAtDesc()
                .stream()
                .map(this::toCategoryResponse)
                .toList();

        List<HomeLessonResponse> recentLessons = lessonRepository
                .findTop4ByStatusTrueOrderByCreatedAtDesc()
                .stream()
                .map(this::toLessonResponse)
                .toList();

        List<HomeLessonResponse> featuredLessons = lessonRepository
                .findTop6ByStatusTrueAndIsFeaturedTrueOrderBySortOrderAscCreatedAtDesc()
                .stream()
                .map(this::toLessonResponse)
                .toList();

        return new HomePageResponse(
                heroLesson == null ? null : toLessonResponse(heroLesson),
                categories,
                recentLessons,
                featuredLessons
        );
    }

    private HomeLessonResponse toLessonResponse(Lesson lesson) {
        Category category = lesson.getCategory();

        return new HomeLessonResponse(
                lesson.getId(),
                lesson.getTitle(),
                lesson.getSummary(),
                lesson.getSlug(),
                lesson.getLevel(),
                lesson.getDurationSeconds(),
                lesson.getCoverImage(),
                category == null ? null : category.getName(),
                category == null ? null : category.getShortName(),
                category == null ? null : category.getSlug()
        );
    }

    private HomeCategoryResponse toCategoryResponse(Category category) {
        return new HomeCategoryResponse(
                category.getId(),
                category.getName(),
                category.getShortName(),
                category.getSlug(),
                category.getCoverImage(),
                lessonRepository.countByCategoryIdAndStatusTrue(category.getId())
        );
    }
}

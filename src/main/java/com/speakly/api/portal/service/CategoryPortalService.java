package com.speakly.api.portal.service;

import com.speakly.api.content.category.repository.CategoryRepository;
import com.speakly.api.content.lesson.repository.LessonRepository;
import com.speakly.api.entity.Category;
import com.speakly.api.entity.Lesson;
import com.speakly.api.portal.dto.CategoryDetailResponse;
import com.speakly.api.portal.dto.CategoryLessonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryPortalService {

    private final CategoryRepository categoryRepository;
    private final LessonRepository lessonRepository;

    @Transactional(readOnly = true)
    public CategoryDetailResponse getCategoryDetailByCode(String categoryCode) {
        Category category = categoryRepository.findBySlugAndStatusTrue(categoryCode)
                .orElseThrow(() -> new RuntimeException("Category not found: " + categoryCode));

        List<Lesson> lessons = lessonRepository
                .findByCategoryIdAndStatusTrueOrderBySortOrderAscCreatedAtDesc(category.getId());

        Lesson featuredLessonEntity = lessons.stream()
                .filter(lesson -> Boolean.TRUE.equals(lesson.getIsFeatured()))
                .findFirst()
                .orElseGet(() -> lessons.isEmpty() ? null : lessons.get(0));

        CategoryLessonResponse featuredLesson = featuredLessonEntity == null
                ? null
                : toCategoryLessonResponse(featuredLessonEntity);

        Integer totalDurationSeconds = lessons.stream()
                .map(Lesson::getDurationSeconds)
                .filter(duration -> duration != null)
                .reduce(0, Integer::sum);

        return CategoryDetailResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .icon(category.getIcon())
                .lessonCount(lessons.size())
                .totalDurationSeconds(totalDurationSeconds)
                .featuredLesson(featuredLesson)
                .lessons(
                        lessons.stream()
                                .map(this::toCategoryLessonResponse)
                                .toList()
                )
                .build();
    }

    private CategoryLessonResponse toCategoryLessonResponse(Lesson lesson) {
        return CategoryLessonResponse.builder()
                .id(lesson.getId())
                .title(lesson.getTitle())
                .slug(lesson.getSlug())
                .summary(lesson.getSummary())
                .coverImage(lesson.getCoverImage())
                .audioUrl(lesson.getAudioUrl())
                .level(lesson.getLevel())
                .durationSeconds(lesson.getDurationSeconds())
                .categoryId(lesson.getCategory() != null ? lesson.getCategory().getId() : null)
                .categoryName(lesson.getCategory() != null ? lesson.getCategory().getName() : null)
                .build();
    }
}

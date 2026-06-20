package com.speakly.api.admin.lesson.service;


import com.speakly.api.admin.category.repository.CategoryRepository;
import com.speakly.api.admin.lesson.dto.LessonDTO;
import com.speakly.api.admin.lesson.dto.LessonQueryDTO;
import com.speakly.api.admin.lesson.repository.LessonRepository;
import com.speakly.api.common.response.PageResponse;
import com.speakly.api.domain.entity.Category;
import com.speakly.api.domain.entity.Lesson;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService{

    private final LessonRepository lessonRepository;
    private final CategoryRepository categoryRepository;


    @Override
    public PageResponse<LessonDTO> list(LessonQueryDTO queryDTO) {
        int current = queryDTO.getCurrent() == null ? 1 : queryDTO.getCurrent();
        int size = queryDTO.getSize() == null ? 10 : queryDTO.getSize();

        Pageable pageable = PageRequest.of(
                current - 1,
                size,
                Sort.by(
                        Sort.Order.asc("sortOrder"),
                        Sort.Order.desc("createdAt")
                )
        );

        Specification<Lesson> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (queryDTO.getCategoryId() != null) {
                predicates.add(
                        cb.equal(root.get("category").get("id"), queryDTO.getCategoryId())
                );
            }

            if (queryDTO.getTitle() != null && !queryDTO.getTitle().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("title")),
                                "%" + queryDTO.getTitle().trim().toLowerCase() + "%"
                        )
                );
            }

            if (queryDTO.getSlug() != null && !queryDTO.getSlug().isBlank()) {
                predicates.add(
                        cb.like(
                                cb.lower(root.get("slug")),
                                "%" + queryDTO.getSlug().trim().toLowerCase() + "%"
                        )
                );
            }

            if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().isBlank()) {
                String keyword = "%" + queryDTO.getKeyword().trim().toLowerCase() + "%";

                predicates.add(
                        cb.or(
                                cb.like(cb.lower(root.get("title")), keyword),
                                cb.like(cb.lower(root.get("summary")), keyword),
                                cb.like(cb.lower(root.get("slug")), keyword)
                        )
                );
            }

            if (queryDTO.getLevel() != null && !queryDTO.getLevel().isBlank()) {
                predicates.add(
                        cb.equal(root.get("level"), queryDTO.getLevel())
                );
            }

            if (queryDTO.getStatus() != null) {
                predicates.add(
                        cb.equal(root.get("status"), queryDTO.getStatus())
                );
            } else {
                predicates.add(
                        cb.equal(root.get("status"), true)
                );
            }

            if (queryDTO.getIsFeatured() != null) {
                predicates.add(
                        cb.equal(root.get("isFeatured"), queryDTO.getIsFeatured())
                );
            }

            if (queryDTO.getIsDaily() != null) {
                predicates.add(
                        cb.equal(root.get("isDaily"), queryDTO.getIsDaily())
                );
            }

            if (queryDTO.getStartTime() != null) {
                predicates.add(
                        cb.greaterThanOrEqualTo(root.get("createdAt"), queryDTO.getStartTime())
                );
            }

            if (queryDTO.getEndTime() != null) {
                predicates.add(
                        cb.lessThanOrEqualTo(root.get("createdAt"), queryDTO.getEndTime())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Lesson> page = lessonRepository.findAll(spec, pageable);

        List<LessonDTO> records = page.getContent()
                .stream()
                .map(this::toDTO)
                .toList();

        return new PageResponse<>(records, current, size, page.getTotalElements());
    }


    @Override
    public LessonDTO detail(Long id) {
        return toDTO(getById(id));
    }

    @Override
    public LessonDTO create(LessonDTO dto) {
        if (lessonRepository.existsBySlug(dto.getSlug())) {
            throw new RuntimeException("Lesson slug already exists");
        }

        Lesson lesson = new Lesson();
        copyToEntity(dto, lesson);

        return toDTO(lessonRepository.save(lesson));
    }


    @Override
    public LessonDTO update(Long id, LessonDTO dto) {
        Lesson lesson = getById(id);
        copyToEntity(dto, lesson);

        return toDTO(lessonRepository.save(lesson));
    }

    @Override
    public void delete(Long id) {
        lessonRepository.delete(getById(id));
    }

    private Lesson getById(Long id) {
        return lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));
    }

    private Category getCategory(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private void copyToEntity(LessonDTO dto, Lesson lesson) {
        lesson.setCategory(getCategory(dto.getCategoryId()));
        lesson.setTitle(dto.getTitle());
        lesson.setSlug(dto.getSlug());
        lesson.setSummary(dto.getSummary());
        lesson.setCoverImage(dto.getCoverImage());
        lesson.setAudioUrl(dto.getAudioUrl());
        lesson.setDurationSeconds(dto.getDurationSeconds());
        lesson.setLevel(dto.getLevel());
        lesson.setTranscript(dto.getTranscript());
        lesson.setIsDaily(dto.getIsDaily());
        lesson.setDailyDate(dto.getDailyDate());
        lesson.setStatus(dto.getStatus());
        lesson.setIsFeatured(dto.getIsFeatured());
        lesson.setSortOrder(dto.getSortOrder());
        lesson.setPublishedAt(dto.getPublishedAt());
    }

    private LessonDTO toDTO(Lesson lesson) {
        LessonDTO dto = new LessonDTO();
        dto.setId(lesson.getId());
        dto.setCategoryId(lesson.getCategory().getId());
        dto.setCategoryName(lesson.getCategory().getName());
        dto.setTitle(lesson.getTitle());
        dto.setSlug(lesson.getSlug());
        dto.setSummary(lesson.getSummary());
        dto.setCoverImage(lesson.getCoverImage());
        dto.setAudioUrl(lesson.getAudioUrl());
        dto.setDurationSeconds(lesson.getDurationSeconds());
        dto.setLevel(lesson.getLevel());
        dto.setTranscript(lesson.getTranscript());
        dto.setStatus(lesson.getStatus());
        dto.setIsFeatured(lesson.getIsFeatured());
        dto.setSortOrder(lesson.getSortOrder());
        dto.setPublishedAt(lesson.getPublishedAt());
        dto.setCreatedAt(lesson.getCreatedAt());
        dto.setUpdatedAt(lesson.getUpdatedAt());
        dto.setIsDaily(lesson.getIsDaily());
        dto.setDailyDate(lesson.getDailyDate());
        return dto;
    }


}

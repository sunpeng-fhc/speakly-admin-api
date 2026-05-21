package com.speakly.api.content.lesson.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.content.category.repository.CategoryRepository;
import com.speakly.api.content.lesson.dto.LessonDTO;
import com.speakly.api.content.lesson.dto.LessonQueryDTO;
import com.speakly.api.content.lesson.repository.LessonRepository;
import com.speakly.api.entity.Category;
import com.speakly.api.entity.Lesson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
                Sort.by(Sort.Direction.ASC, "sortOrder", "id")
        );

        Page<Lesson> page;

        if (queryDTO.getCategoryId() != null) {
            page = lessonRepository.findByCategoryId(queryDTO.getCategoryId(), pageable);
        } else if (queryDTO.getKeyword() != null && !queryDTO.getKeyword().isBlank()) {
            page = lessonRepository.findByTitleContainingIgnoreCase(queryDTO.getKeyword(), pageable);
        } else if (queryDTO.getLevel() != null && !queryDTO.getLevel().isBlank()) {
            page = lessonRepository.findByLevel(queryDTO.getLevel(), pageable);
        } else if (queryDTO.getStatus() != null) {
            page = lessonRepository.findByStatus(queryDTO.getStatus(), pageable);
        } else {
            page = lessonRepository.findAll(pageable);
        }

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
        return dto;
    }


}

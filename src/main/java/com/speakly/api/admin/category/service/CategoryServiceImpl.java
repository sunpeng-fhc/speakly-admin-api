package com.speakly.api.admin.category.service;


import com.speakly.api.admin.category.dto.CategoryDTO;
import com.speakly.api.admin.category.dto.CategoryQueryDTO;
import com.speakly.api.admin.category.repository.CategoryRepository;
import com.speakly.api.common.response.PageResponse;
import com.speakly.api.domain.entity.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{

    private final CategoryRepository categoryRepository;

    @Override
    public PageResponse<CategoryDTO> list(CategoryQueryDTO queryDTO) {
        int current = queryDTO.getCurrent() == null ? 1 : queryDTO.getCurrent();
        int size = queryDTO.getSize() == null ? 10 : queryDTO.getSize();

        Pageable pageable = PageRequest.of(
                current - 1,
                size,
                Sort.by(Sort.Direction.ASC, "sort_order", "id")
        );

        Page<Category> page = categoryRepository.searchCategories(
                queryDTO.getName(),
                queryDTO.getShortName(),
                queryDTO.getSlug(),
                queryDTO.getDescription(),
                queryDTO.getStatus(),
                pageable
        );

        List<CategoryDTO> records = page.getContent()
                .stream()
                .map(this::toDTO)
                .toList();

        return new PageResponse<>(records, current, size, page.getTotalElements());
    }

    @Override
    public CategoryDTO detail(Long id) {
        return toDTO(getById(id));
    }

    @Override
    public CategoryDTO create(CategoryDTO dto) {
        if (categoryRepository.existsBySlug(dto.getSlug())) {
            throw new RuntimeException("Category slug already exists");
        }

        Category category = new Category();
        copyToEntity(dto, category);

        return toDTO(categoryRepository.save(category));
    }

    @Override
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = getById(id);
        copyToEntity(dto, category);

        return toDTO(categoryRepository.save(category));
    }

    @Override
    public void delete(Long id) {
        categoryRepository.delete(getById(id));
    }

    private Category getById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    private void copyToEntity(CategoryDTO dto, Category category) {
        category.setName(dto.getName());
        category.setShortName(dto.getShortName());
        category.setSlug(dto.getSlug());
        category.setDescription(dto.getDescription());
        category.setIcon(dto.getIcon());
        category.setCoverImage(dto.getCoverImage());
        category.setThemeColor(dto.getThemeColor());
        category.setSortOrder(dto.getSortOrder());
        category.setIsFeatured(dto.getIsFeatured());
        category.setStatus(dto.getStatus());
    }

    private CategoryDTO toDTO(Category category) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(category.getId());
        dto.setName(category.getName());
        dto.setShortName(category.getShortName());
        dto.setSlug(category.getSlug());
        dto.setDescription(category.getDescription());
        dto.setIcon(category.getIcon());
        dto.setCoverImage(category.getCoverImage());
        dto.setThemeColor(category.getThemeColor());
        dto.setSortOrder(category.getSortOrder());
        dto.setIsFeatured(category.getIsFeatured());
        dto.setStatus(category.getStatus());
        dto.setCreatedAt(category.getCreatedAt());
        dto.setUpdatedAt(category.getUpdatedAt());
        return dto;
    }
}

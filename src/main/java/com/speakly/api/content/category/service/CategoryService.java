package com.speakly.api.content.category.service;

import com.speakly.api.common.PageResponse;
import com.speakly.api.content.category.dto.CategoryDTO;
import com.speakly.api.content.category.dto.CategoryQueryDTO;

import java.util.List;

public interface CategoryService {

    PageResponse<CategoryDTO> list(CategoryQueryDTO queryDTO);

    CategoryDTO detail(Long id);

    CategoryDTO create(CategoryDTO dto);

    CategoryDTO update(Long id, CategoryDTO dto);

    void delete(Long id);
}
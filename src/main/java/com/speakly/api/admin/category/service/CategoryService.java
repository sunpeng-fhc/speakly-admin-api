package com.speakly.api.admin.category.service;


import com.speakly.api.admin.category.dto.CategoryDTO;
import com.speakly.api.admin.category.dto.CategoryQueryDTO;
import com.speakly.api.common.response.PageResponse;

public interface CategoryService {

    PageResponse<CategoryDTO> list(CategoryQueryDTO queryDTO);

    CategoryDTO detail(Long id);

    CategoryDTO create(CategoryDTO dto);

    CategoryDTO update(Long id, CategoryDTO dto);

    void delete(Long id);
}
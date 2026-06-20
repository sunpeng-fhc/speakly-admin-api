package com.speakly.api.admin.category.controller;


import com.speakly.api.admin.category.dto.CategoryDTO;
import com.speakly.api.admin.category.dto.CategoryQueryDTO;
import com.speakly.api.admin.category.service.CategoryService;
import com.speakly.api.common.response.ApiResponse;
import com.speakly.api.common.response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping("/list")
    public ApiResponse<PageResponse<CategoryDTO>> list(CategoryQueryDTO queryDTO) {
        return ApiResponse.success(categoryService.list(queryDTO));
    }

    @GetMapping("/detail/{id}")
    public ApiResponse<CategoryDTO> detail(@PathVariable Long id) {
        return ApiResponse.success(categoryService.detail(id));
    }

    @PostMapping("/create")
    public ApiResponse<CategoryDTO> create(@RequestBody CategoryDTO dto) {
        return ApiResponse.success(categoryService.create(dto));
    }

    @PutMapping("/update/{id}")
    public ApiResponse<CategoryDTO> update(@PathVariable Long id, @RequestBody CategoryDTO dto) {
        return ApiResponse.success(categoryService.update(id, dto));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return ApiResponse.success(null, "删除成功");
    }
}
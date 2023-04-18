package com.example.university.controller;

import com.example.university.dto.request.CategoryRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/category/")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("add")
    public ApiResponse addCategory(@RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.add(categoryRequestDto);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateCategory(
            @PathVariable UUID id,
            @RequestBody CategoryRequestDto categoryRequestDto) {
        return categoryService.update(id, categoryRequestDto);
    }

    @DeleteMapping("del/{id}")
    public ApiResponse deleteCategory(@PathVariable UUID id) {
        return categoryService.delete(id);
    }

    @GetMapping("get-main")
    public ApiResponse getAllTopCategory() {
        return categoryService.topCategoryList();
    }

    @GetMapping("get-all")
    public ApiResponse getAllCategory() {
        return categoryService.getAllCategories();
    }
}

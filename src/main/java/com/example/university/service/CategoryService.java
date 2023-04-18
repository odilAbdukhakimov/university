package com.example.university.service;

import com.example.university.dto.request.CategoryRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.dto.response.CategoryResponseDto;
import com.example.university.entity.CategoryEntity;
import com.example.university.exception.RecordNotFound;
import com.example.university.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public ApiResponse topCategoryList() {
        List<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryEntity> topCategory = all.stream().filter((c) ->
                getTopCategoryById(c.getId()) == null).toList();
        List<CategoryResponseDto> responseTopCategory = topCategory.stream().map(CategoryResponseDto::from).toList();
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                responseTopCategory
        );
    }

    public ApiResponse add(CategoryRequestDto requestDto) {
        CategoryEntity of = CategoryEntity.of(requestDto);
        CategoryEntity categoryEntity = categoryRepository.save(of);
        if (requestDto.getTopCategoryId() != null) {
            CategoryEntity byId = getById(requestDto.getTopCategoryId());
            List<CategoryEntity> subcategories = byId.getSubcategory();
            if (subcategories == null) {
                byId.setSubcategory(List.of(categoryEntity));
            } else {
                subcategories.add(categoryEntity);
                byId.setSubcategory(subcategories);
            }
            categoryRepository.save(byId);
        }
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                CategoryResponseDto.from(categoryEntity)
        );
    }

    public ApiResponse update(UUID id, CategoryRequestDto categoryRequestDto) {
        CategoryEntity byId = getById(id);
        if (categoryRequestDto.getNameRu() != null && !categoryRequestDto.getNameRu().equals("")) {
            byId.setNameRu(categoryRequestDto.getNameRu());
        }
        if (categoryRequestDto.getNameUz() != null && !categoryRequestDto.getNameUz().equals("")) {
            byId.setNameUz(categoryRequestDto.getNameUz());
        }
        if (categoryRequestDto.getNameEng() != null && !categoryRequestDto.getNameEng().equals("")) {
            byId.setNameEng(categoryRequestDto.getNameEng());
        }
        if (categoryRequestDto.getTopCategoryId() != null && !categoryRequestDto.getTopCategoryId().equals("")) {
            //--------old category----------//
            CategoryEntity oldCategory = getTopCategoryById(byId.getId());
            List<CategoryEntity> subcategories = Objects.requireNonNull(oldCategory).getSubcategory();
            subcategories.remove(byId);
            oldCategory.setSubcategory(subcategories);
            categoryRepository.save(oldCategory);
            //--------new category----------//
            CategoryEntity newCategory = getById(categoryRequestDto.getTopCategoryId());
            List<CategoryEntity> subcategoriesNew = newCategory.getSubcategory();
            if (subcategoriesNew == null) {
                newCategory.setSubcategory(List.of(byId));
            } else {
                subcategoriesNew.add(byId);
                newCategory.setSubcategory(subcategoriesNew);
            }
            categoryRepository.save(newCategory);
        }
        CategoryEntity saved = categoryRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                CategoryResponseDto.from(saved)
        );
    }

    public ApiResponse delete(UUID id) {
        CategoryEntity byId = getById(id);
        categoryRepository.delete(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byId
        );
    }

    public ApiResponse getAllCategories() {
        List<CategoryEntity> all = categoryRepository.findAll();
        List<CategoryResponseDto> responseDtoList = all.stream().map(CategoryResponseDto::from).toList();
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                responseDtoList
        );
    }


    private CategoryEntity getById(UUID id) {
        return categoryRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("Category not found"));
    }

    private CategoryEntity getTopCategoryById(UUID id) {
        for (CategoryEntity category : categoryRepository.findAll()) {
            for (CategoryEntity categoryEntity : category.getSubcategory()) {
                if (categoryEntity.getId().equals(id))
                    return categoryEntity;
            }

        }
        return null;

    }
}

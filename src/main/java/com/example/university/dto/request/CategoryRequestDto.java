package com.example.university.dto.request;

import com.example.university.entity.CategoryEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryRequestDto {
    private String nameRu;
    private String nameUz;
    private String nameEng;
    private UUID topCategoryId;
}

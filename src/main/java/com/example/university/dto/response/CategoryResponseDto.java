package com.example.university.dto.response;

import com.example.university.entity.CategoryEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CategoryResponseDto {
    private UUID id;
    @JsonProperty("name_ru")
    private String nameRu;
    @JsonProperty("name_Uz")
    private String nameUz;
    @JsonProperty("name_Eng")
    private String nameEng;
    private List<CategoryEntity> subcategories;

    public static CategoryResponseDto from(CategoryEntity entity) {
        return CategoryResponseDto.builder()
                .id(entity.getId())
                .nameRu(entity.getNameRu())
                .nameUz(entity.getNameUz())
                .nameEng(entity.getNameEng())
                .subcategories(entity.getSubcategory())
                .build();
    }
}

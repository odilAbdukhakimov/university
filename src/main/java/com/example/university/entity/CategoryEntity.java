package com.example.university.entity;

import com.example.university.dto.request.CategoryRequestDto;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoryEntity extends BaseEntity {
    private String nameRu;
    private String nameUz;
    private String nameEng;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id")
    private List<CategoryEntity> subcategory;

    public static CategoryEntity of(CategoryRequestDto dto) {
        return CategoryEntity.builder()
                .nameRu(dto.getNameRu())
                .nameUz(dto.getNameUz())
                .nameEng(dto.getNameEng())
                .build();
    }
}

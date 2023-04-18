package com.example.university.dto.request;

import com.example.university.entity.CategoryEntity;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.util.UUID;

@Data
public class AboutUsRequestDto {
    private String aboutTypeRu;
    private String aboutTypeUz;
    private String aboutTypeEng;
    private String contentRu;
    private String contentUz;
    private String contentEng;
    private String photoUrl;
    private UUID categoryId;
}

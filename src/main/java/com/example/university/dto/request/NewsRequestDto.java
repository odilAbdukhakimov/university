package com.example.university.dto.request;

import com.example.university.entity.CategoryEntity;
import lombok.Data;

import java.util.UUID;

@Data
public class NewsRequestDto {
    private String mainTopicRu;
    private String mainTopicUz;
    private String mainTopicEng;
    private String contentRu;
    private String contentUz;
    private String contentEng;
    private String photoUrl;
    private String type;
    private UUID categoryId;
}

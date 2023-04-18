package com.example.university.dto.response;

import com.example.university.entity.CategoryEntity;
import com.example.university.entity.NewsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NewsResponseDto {
    private UUID id;
    @JsonProperty("main_topic_ru")
    private String mainTopicRu;
    @JsonProperty("main_topic_uz")
    private String mainTopicUz;
    @JsonProperty("main_topic_eng")
    private String mainTopicEng;
    @JsonProperty("content_ru")
    private String contentRu;
    @JsonProperty("content_uz")
    private String contentUz;
    @JsonProperty("content_eng")
    private String contentEng;
    @JsonProperty("photo_url")
    private String photoUrl;
    private String type;
    @JsonProperty("date_time")
    private LocalDateTime dateTime;
    private CategoryEntity category;

    public static NewsResponseDto from(NewsEntity entity) {
        return NewsResponseDto.builder()
                .id(entity.getId())
                .mainTopicRu(entity.getMainTopicRu())
                .mainTopicUz(entity.getMainTopicUz())
                .mainTopicEng(entity.getMainTopicEng())
                .contentRu(entity.getContentRu())
                .contentUz(entity.getContentUz())
                .contentEng(entity.getContentEng())
                .photoUrl(entity.getPhotoUrl())
                .type(entity.getType())
                .dateTime(entity.getDateTime())
                .category(entity.getCategory())
                .build();
    }
}

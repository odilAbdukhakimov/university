package com.example.university.dto.response;

import com.example.university.entity.AboutUsEntity;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class AboutUsResponseDto {
    private UUID id;
    @JsonProperty("about_type_ru")
    private String aboutTypeRu;
    @JsonProperty("about_type_uz")
    private String aboutTypeUz;
    @JsonProperty("about_type_eng")
    private String aboutTypeEng;
    @JsonProperty("content_ru")
    private String contentRu;
    @JsonProperty("content_uz")
    private String contentUz;
    @JsonProperty("content_eng")
    private String contentEng;
    @JsonProperty("photo_url")
    private String photoUrl;
    private String category;
    public static AboutUsResponseDto from(AboutUsEntity entity){
        return AboutUsResponseDto.builder()
                .id(entity.getId())
                .aboutTypeRu(entity.getAboutTypeRu())
                .aboutTypeUz(entity.getAboutTypeUz())
                .aboutTypeEng(entity.getAboutTypeEng())
                .contentRu(entity.getContentRu())
                .contentUz(entity.getContentUz())
                .contentEng(entity.getContentEng())
                .photoUrl(entity.getPhotoUrl())
                .category("category")
                .build();
    }
}

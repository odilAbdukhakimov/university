package com.example.university.entity;

import com.example.university.dto.request.NewsRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewsEntity extends BaseEntity {
    private String mainTopicRu;
    private String mainTopicUz;
    private String mainTopicEng;
    @Column(length = 1000)
    private String contentRu;
    @Column(length = 1000)
    private String contentUz;
    @Column(length = 1000)
    private String contentEng;
    private String photoUrl;
    private String type;
    private LocalDateTime dateTime;
    @OneToOne
    private CategoryEntity category;

    public static NewsEntity of(NewsRequestDto dto) {
        return NewsEntity.builder()
                .mainTopicRu(dto.getMainTopicRu())
                .mainTopicUz(dto.getMainTopicUz())
                .mainTopicEng(dto.getMainTopicEng())
                .contentRu(dto.getContentRu())
                .contentUz(dto.getContentUz())
                .contentEng(dto.getContentEng())
                .photoUrl(dto.getPhotoUrl())
                .type(dto.getType())
                .dateTime(LocalDateTime.now())
                .build();
    }
}

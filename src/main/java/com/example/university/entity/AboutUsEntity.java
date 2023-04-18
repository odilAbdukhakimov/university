package com.example.university.entity;

import com.example.university.dto.request.AboutUsRequestDto;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AboutUsEntity extends BaseEntity {
    private String aboutTypeRu;
    private String aboutTypeUz;
    private String aboutTypeEng;
    @Column(length = 1000)
    private String contentRu;
    @Column(length = 1000)
    private String contentUz;
    @Column(length = 1000)
    private String contentEng;
    private String photoUrl;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private CategoryEntity category;

    public static AboutUsEntity of(AboutUsRequestDto dto){
        return AboutUsEntity.builder()
                .aboutTypeRu(dto.getAboutTypeRu())
                .aboutTypeUz(dto.getAboutTypeUz())
                .aboutTypeEng(dto.getAboutTypeEng())
                .contentRu(dto.getContentRu())
                .contentUz(dto.getContentUz())
                .contentEng(dto.getContentEng())
                .photoUrl(dto.getPhotoUrl())
                .build();
    }
}

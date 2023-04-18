package com.example.university.service;

import com.example.university.dto.request.AboutUsRequestDto;
import com.example.university.dto.response.AboutUsResponseDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.entity.AboutUsEntity;
import com.example.university.entity.CategoryEntity;
import com.example.university.exception.RecordNotFound;
import com.example.university.repository.AboutUsRepository;
import com.example.university.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AboutUsService {
    private final AboutUsRepository aboutUsRepository;
    private final CategoryRepository categoryRepository;

    public ApiResponse add(AboutUsRequestDto dto) {
        AboutUsEntity of = AboutUsEntity.of(dto);
        if (dto.getCategoryId() != null) {
            CategoryEntity category = categoryRepository.findById(dto.getCategoryId()).orElseThrow(
                    () -> new RecordNotFound("Category not found")
            );
            of.setCategory(category);
        }
        AboutUsEntity save = aboutUsRepository.save(of);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                AboutUsResponseDto.from(save)
        );
    }

    public ApiResponse delete(UUID id) {
        AboutUsEntity byId = getById(id);
        aboutUsRepository.delete(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byId
        );
    }

    public ApiResponse update(UUID id, AboutUsRequestDto dto) {
        AboutUsEntity byId = getById(id);
        if (dto.getCategoryId() != null && !dto.getCategoryId().equals("")) {
            CategoryEntity categoryEntity = categoryRepository.findById(dto.getCategoryId()).orElseThrow(() ->
                    new RecordNotFound(("Category not found")));
            byId.setCategory(categoryEntity);
        }
        if (dto.getAboutTypeRu() != null && !dto.getAboutTypeRu().equals("")) {
            byId.setAboutTypeRu(dto.getAboutTypeRu());
        }
        if (dto.getAboutTypeUz() != null && !dto.getAboutTypeUz().equals("")) {
            byId.setAboutTypeUz(dto.getAboutTypeUz());
        }
        if (dto.getAboutTypeEng() != null && !dto.getAboutTypeEng().equals("")) {
            byId.setAboutTypeEng(dto.getAboutTypeEng());
        }
        if (dto.getContentRu() != null && !dto.getContentRu().equals("")) {
            byId.setContentRu(dto.getContentRu());
        }
        if (dto.getContentUz() != null && !dto.getContentUz().equals("")) {
            byId.setContentUz(dto.getContentUz());
        }
        if (dto.getContentEng() != null && !dto.getContentEng().equals("")) {
            byId.setContentEng(dto.getContentEng());
        }
        if (dto.getPhotoUrl() != null && !dto.getPhotoUrl().equals("")) {
            byId.setPhotoUrl(dto.getPhotoUrl());
        }
        AboutUsEntity save = aboutUsRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                AboutUsResponseDto.from(save)
        );
    }

    public ApiResponse getAll() {
        List<AboutUsEntity> all = aboutUsRepository.findAll();
        List<AboutUsResponseDto> collect = all.stream().map(AboutUsResponseDto::from).collect(Collectors.toList());
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                collect
        );
    }

    public ApiResponse getByCategoryId(UUID categoryId) {
        AboutUsEntity byCategoryId = aboutUsRepository.findByCategory_Id(categoryId).orElseThrow(() ->
                new RecordNotFound("Category not found"));
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byCategoryId
        );
    }

    private AboutUsEntity getById(UUID id) {
        return aboutUsRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("Not found aboutUsClass"));
    }
}

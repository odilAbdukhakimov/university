package com.example.university.service;

import com.example.university.dto.request.NewsRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.dto.response.NewsResponseDto;
import com.example.university.entity.CategoryEntity;
import com.example.university.entity.NewsEntity;
import com.example.university.exception.RecordNotFound;
import com.example.university.repository.CategoryRepository;
import com.example.university.repository.NewsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final CategoryRepository categoryRepository;
    private final NewsRepository newsRepository;

    public ApiResponse add(NewsRequestDto newsRequestDto) {
        NewsEntity of = NewsEntity.of(newsRequestDto);
        if (newsRequestDto.getCategoryId() != null) {
            CategoryEntity categoryEntity = categoryRepository.findById(newsRequestDto.getCategoryId()).orElseThrow(() ->
                    new RecordNotFound("Category not found"));
            of.setCategory(categoryEntity);
        }
        NewsEntity save = newsRepository.save(of);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                NewsResponseDto.from(save)
        );
    }

    public ApiResponse update(UUID id, NewsRequestDto newsRequestDto) {
        NewsEntity byId = getById(id);
        if (newsRequestDto.getMainTopicRu() != null && !newsRequestDto.getMainTopicRu().equals("")) {
            byId.setMainTopicRu(newsRequestDto.getMainTopicRu());
        }
        if (newsRequestDto.getMainTopicUz() != null && !newsRequestDto.getMainTopicUz().equals("")) {
            byId.setMainTopicUz(newsRequestDto.getMainTopicUz());
        }
        if (newsRequestDto.getMainTopicEng() != null && !newsRequestDto.getMainTopicEng().equals("")) {
            byId.setMainTopicEng(newsRequestDto.getMainTopicEng());
        }
        if (newsRequestDto.getContentRu() != null && !newsRequestDto.getContentRu().equals("")) {
            byId.setContentRu(newsRequestDto.getContentRu());
        }
        if (newsRequestDto.getContentUz() != null && !newsRequestDto.getContentUz().equals("")) {
            byId.setContentUz(newsRequestDto.getContentUz());
        }
        if (newsRequestDto.getContentEng() != null && !newsRequestDto.getContentEng().equals("")) {
            byId.setContentEng(newsRequestDto.getContentEng());
        }
        if (newsRequestDto.getPhotoUrl() != null && !newsRequestDto.getPhotoUrl().equals(""))
            byId.setPhotoUrl(newsRequestDto.getPhotoUrl());
        if (newsRequestDto.getType() != null && !newsRequestDto.getType().equals("")) {
            byId.setType(newsRequestDto.getType());
        }
        NewsEntity save = newsRepository.save(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                NewsResponseDto.from(save)
        );
    }

    public ApiResponse delete(UUID id) {
        NewsEntity byId = getById(id);
        newsRepository.delete(byId);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                byId
        );
    }

    public ApiResponse getAllByOrder() {
        List<NewsEntity> all = newsRepository.findByOrderByDateTimeDesc();
        List<NewsResponseDto> collect = all.stream().map(NewsResponseDto::from).toList();
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                collect
        );
    }

    private NewsEntity getById(UUID id) {
        return newsRepository.findById(id).orElseThrow(() ->
                new RecordNotFound("News not found"));
    }
}

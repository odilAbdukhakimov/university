package com.example.university.controller;

import com.example.university.dto.request.NewsRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/news/")
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @PostMapping("add")
    public ApiResponse addNews(@RequestBody NewsRequestDto newsRequestDto) {
        return newsService.add(newsRequestDto);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateNews(
            @PathVariable UUID id,
            @RequestBody NewsRequestDto newsRequestDto
    ) {
        return newsService.update(id, newsRequestDto);
    }

    @DeleteMapping("del/{id}")
    public ApiResponse deleteNews(@PathVariable UUID id) {
        return newsService.delete(id);
    }

    @GetMapping("get-main")
    public ApiResponse getNews() {
        return newsService.getAllByOrder();
    }
}

package com.example.university.controller;

import com.example.university.dto.request.AboutUsRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.service.AboutUsService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/about/")
@RequiredArgsConstructor
public class AboutUsController {
    private final AboutUsService aboutUsService;

    @PostMapping("add")
    public ApiResponse addAboutUs(@RequestBody AboutUsRequestDto aboutUsRequestDto) {
        return aboutUsService.add(aboutUsRequestDto);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateAboutUs(
            @PathVariable UUID id,
            @RequestBody AboutUsRequestDto aboutUsRequestDto
    ) {
        return aboutUsService.update(id, aboutUsRequestDto);
    }

    @DeleteMapping("del/{id}")
    public ApiResponse deleteAboutUs(@PathVariable UUID id) {
        return aboutUsService.delete(id);
    }

    @GetMapping("get-all")
    public ApiResponse getAllAboutUs() {
        return aboutUsService.getAll();
    }

    @GetMapping("get-by/{categoryId}")
    public ApiResponse getByCategoryId(@PathVariable UUID categoryId) {
        return aboutUsService.getByCategoryId(categoryId);
    }
}

package com.example.university.controller;

import com.example.university.dto.request.ContactRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.service.ContactService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/contact/")
@RequiredArgsConstructor
public class ContactController {
    private final ContactService contactService;

    @PostMapping("add")
    public ApiResponse addContact(@RequestBody ContactRequestDto contactRequestDto) {
        return contactService.add(contactRequestDto);
    }

    @PutMapping("update/{id}")
    public ApiResponse updateContact(
            @PathVariable UUID id,
            @RequestBody ContactRequestDto contactRequestDto) {
        return contactService.update(id, contactRequestDto);
    }

    @GetMapping("get")
    public ApiResponse getOne() {
        return contactService.getOne();
    }

    @DeleteMapping("del/{id}")
    public ApiResponse delete(@PathVariable UUID id) {
        return contactService.delete(id);
    }
}

package com.example.university.controller;

import com.example.university.dto.response.ApiResponse;
import com.example.university.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/user/")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PutMapping("update/{id}/{status}")
    public ApiResponse editLocked(@PathVariable UUID id, @PathVariable Boolean status) {
        return userService.editAccountLocked(id, status);
    }

    @GetMapping("get-main")
    public ApiResponse getAllUser() {
        return userService.gatAllUsers();
    }
}

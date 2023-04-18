package com.example.university.service;

import com.example.university.dto.response.ApiResponse;
import com.example.university.entity.UserEntity;
import com.example.university.exception.RecordNotFound;
import com.example.university.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ApiResponse gatAllUsers() {
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                userRepository.findAll()
        );
    }

    public ApiResponse editAccountLocked(UUID id, Boolean status) {
        UserEntity byId = getById(id);
        if (status != null) {
            byId.setAccountLocked(status);
            return new ApiResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.name(),
                    userRepository.save(byId)
            );
        }
        return new ApiResponse(
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                null
        );
    }

    private UserEntity getById(UUID id) {
        Optional<UserEntity> byId = userRepository.findById(id);
        return byId.orElseThrow(() -> new RecordNotFound("User not found"));
    }
}

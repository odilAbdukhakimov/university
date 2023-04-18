package com.example.university.service;

import com.example.university.dto.request.UserRegisterDto;
import com.example.university.dto.request.UsernamePasswordRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.dto.response.UserResponseDto;
import com.example.university.entity.UserEntity;
import com.example.university.exception.RecordNotFound;
import com.example.university.repository.UserRepository;
import com.example.university.utils.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    public ApiResponse register(UserRegisterDto userRegisterDto) {
        UserEntity build = UserEntity.builder()
                .name(userRegisterDto.getName())
                .password(passwordEncoder.encode(userRegisterDto.getPassword()))
                .perRoleEnumList(userRegisterDto.getPerRoleEnumList())
                .username(userRegisterDto.getUsername())
                .build();
        UserEntity save = userRepository.save(build);
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                UserResponseDto.from(save)
        );
    }

    public ApiResponse login(UsernamePasswordRequestDto usernamePasswordRequestDto) throws UsernameNotFoundException, JsonProcessingException {
        UserEntity userEntity = userRepository.findByUsername(usernamePasswordRequestDto.getUsername()).orElseThrow(() ->
                new RecordNotFound("username or password is incorrect"));
        if (!passwordEncoder.matches(usernamePasswordRequestDto.getPassword(), userEntity.getPassword())) {
            throw new RecordNotFound("username or password is incorrect");
        }
        return new ApiResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                Map.of(
                        "access_token", jwtUtils.generateAccessToken(userEntity),
                        "refresh_token", jwtUtils.generateRefreshToken(userEntity)
                )
        );
    }

    public ApiResponse getAccessToken(String refreshToken) throws JsonProcessingException {
        Claims claims = jwtUtils.isValidRefreshToken(refreshToken);
        if (claims != null) {
            String username = claims.getSubject();
            UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
            if (userEntity != null) {
                return new ApiResponse(
                        HttpStatus.OK.value(),
                        HttpStatus.OK.name(),
                        Map.of(
                                "access_token", jwtUtils.generateAccessToken(userEntity)
                        )
                );
            }
        }
        return null;
    }
}

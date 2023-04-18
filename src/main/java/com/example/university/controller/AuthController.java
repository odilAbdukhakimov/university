package com.example.university.controller;

import com.example.university.dto.request.UserRegisterDto;
import com.example.university.dto.request.UsernamePasswordRequestDto;
import com.example.university.dto.response.ApiResponse;
import com.example.university.service.AuthService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ApiResponse register(@RequestBody UserRegisterDto userRegisterDto){
        return authService.register(userRegisterDto);
    }
    @PostMapping("login")
    public ApiResponse login(@RequestBody UsernamePasswordRequestDto usernamePasswordRequestDto) throws JsonProcessingException {
        return authService.login(usernamePasswordRequestDto);
    }

    @PostMapping("refresh/token/blamlahaljlaslagascasckhascohaohscihqawfuhojaoihscoacos")
    public ApiResponse getAccessTokenByRefreshToken(
            @RequestBody Map<String,String> refreshToken
    ) throws JsonProcessingException {
        return authService.getAccessToken(refreshToken.get("refresh_token"));
    }


}

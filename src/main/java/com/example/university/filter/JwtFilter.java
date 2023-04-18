package com.example.university.filter;

import com.example.university.entity.UserEntity;
import com.example.university.service.RedisCacheService;
import com.example.university.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final RedisCacheService redisCacheService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain
    ) throws ServletException, IOException {
        String requestHeader = request.getHeader("Authorization");
        if (requestHeader == null || !requestHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = requestHeader.replace("Bearer ", "");
        Claims claims = jwtUtils.isValidAccessToken(token);

        if (claims == null) {
            filterChain.doFilter(request, response);
            return;
        }
        String redisKey = claims.getSubject();
        UserEntity userEntity = redisCacheService.getUserFromRedis(redisKey);
        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userEntity.getUsername(),
                        null,
                        userEntity.getAuthorities()
                );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<String> permissions) {
        List<SimpleGrantedAuthority> permissionList = new ArrayList<>();
        permissions.forEach((role) -> {
            permissionList.add(new SimpleGrantedAuthority(role));
        });
        return permissionList;
    }
}

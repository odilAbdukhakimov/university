package com.example.university.dto.response;

import com.example.university.entity.UserEntity;
import com.example.university.entity.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserResponseDto {
    private UUID id;
    private String name;
    private String username;
    @JsonProperty("redis_key")
    private String redisKey;
    @JsonProperty("photo_url")
    private String photoUrl;
    @JsonProperty("account_non_locked")
    private boolean accountNonLocked;
    @Enumerated(EnumType.STRING)
    @JsonProperty("role_enum_list")
    private List<RoleEnum> perRoleEnumList;
    public static UserResponseDto from(UserEntity entity){
        return UserResponseDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .username(entity.getUsername())
                .redisKey(entity.getRedisKey())
                .photoUrl(entity.getPhotoUrl())
                .accountNonLocked(entity.isAccountLocked())
                .perRoleEnumList(entity.getPerRoleEnumList())
                .build();
    }
}

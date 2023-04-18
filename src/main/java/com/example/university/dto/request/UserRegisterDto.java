package com.example.university.dto.request;

import com.example.university.entity.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;


@Data
public class UserRegisterDto {

    private String name;
    private String username;
    private String password;
    @JsonProperty("roles")
    private List<RoleEnum> perRoleEnumList;

}

package com.example.university.entity;

import com.example.university.entity.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity extends BaseEntity implements UserDetails {
    private String name;
    private String username;
    private String password;
    private String redisKey;
    private String photoUrl;
    private boolean accountLocked = true;
    @Enumerated(EnumType.STRING)
    private List<RoleEnum> perRoleEnumList;

    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> roles = new ArrayList<>();

        perRoleEnumList.forEach((role) -> {
            roles.add(new SimpleGrantedAuthority("ROLE_" + role));
        });
        return roles;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
//    public void setAccountLocked(boolean status){
//        this.isAccountLocked()=
//    }
}

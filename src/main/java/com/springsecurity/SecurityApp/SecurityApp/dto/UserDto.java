package com.springsecurity.SecurityApp.SecurityApp.dto;

import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Permission;
import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {

    private String email, name;

    private Long id;

    private Set<Role> roles;

    private Set<Permission> permissions;
}

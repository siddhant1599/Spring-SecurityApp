package com.springsecurity.SecurityApp.SecurityApp.dto;

import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Permission;
import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    private String email,password,name;
    private Set<Role> roles;
    private Set<Permission> permissions;
}

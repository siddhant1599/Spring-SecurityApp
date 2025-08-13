package com.springsecurity.SecurityApp.SecurityApp.dto;

import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Role;
import lombok.Data;

import java.util.Set;

@Data
public class SignUpDto {
    String email,password,name;
    Set<Role> roles;
}

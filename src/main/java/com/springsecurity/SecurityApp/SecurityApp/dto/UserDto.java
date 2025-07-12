package com.springsecurity.SecurityApp.SecurityApp.dto;

import lombok.Data;

@Data
public class UserDto {

    private String email, name;

    private Long id;
}

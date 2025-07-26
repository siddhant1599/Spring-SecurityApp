package com.springsecurity.SecurityApp.SecurityApp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    String email, password;
}

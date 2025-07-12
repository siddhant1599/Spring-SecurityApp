package com.springsecurity.SecurityApp.SecurityApp.controllers;

import com.springsecurity.SecurityApp.SecurityApp.dto.LoginDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.SignUpDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.UserDto;
import com.springsecurity.SecurityApp.SecurityApp.services.AuthService;
import com.springsecurity.SecurityApp.SecurityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {


    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUpUser(@RequestBody SignUpDto signUpDto){
        UserDto user = userService.signUpUser(signUpDto);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        String token  = authService.login(loginDto);

        Cookie cookie = new Cookie("token",token);
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(token);
    }
}

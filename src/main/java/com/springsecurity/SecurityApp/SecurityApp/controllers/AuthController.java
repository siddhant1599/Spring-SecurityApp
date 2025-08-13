package com.springsecurity.SecurityApp.SecurityApp.controllers;

import com.springsecurity.SecurityApp.SecurityApp.dto.LoginDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.LoginResponseDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.SignUpDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.UserDto;
import com.springsecurity.SecurityApp.SecurityApp.services.AuthService;
import com.springsecurity.SecurityApp.SecurityApp.services.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

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
    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginDto loginDto, HttpServletResponse httpServletResponse){
        LoginResponseDto loginResponseDto  = authService.login(loginDto);

        Cookie cookie = new Cookie("refresh_token", loginResponseDto.getRefreshToken());
        httpServletResponse.addCookie(cookie);

        return ResponseEntity.ok(loginResponseDto);
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();

        String refreshToken = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals("refresh_token"))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        LoginResponseDto loginResponseDto = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(loginResponseDto);
    }


}

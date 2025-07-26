package com.springsecurity.SecurityApp.SecurityApp.services;

import com.springsecurity.SecurityApp.SecurityApp.dto.LoginDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.LoginResponseDto;
import com.springsecurity.SecurityApp.SecurityApp.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    public LoginResponseDto login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        return  new LoginResponseDto(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDto refreshToken(String refreshToken) {

        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User currentUser = userService.getUserById(userId);


        String accessToken = jwtService.generateAccessToken(currentUser);
        return new LoginResponseDto(currentUser.getId(), accessToken, refreshToken);

    }
}

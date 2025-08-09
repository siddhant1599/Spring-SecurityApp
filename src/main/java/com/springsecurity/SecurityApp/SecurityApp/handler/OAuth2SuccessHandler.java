package com.springsecurity.SecurityApp.SecurityApp.handler;

import com.springsecurity.SecurityApp.SecurityApp.entities.User;
import com.springsecurity.SecurityApp.SecurityApp.repositories.UserRepository;
import com.springsecurity.SecurityApp.SecurityApp.services.JwtService;
import com.springsecurity.SecurityApp.SecurityApp.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken authToken = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User oAuth2User = (DefaultOAuth2User) authToken.getPrincipal();

        log.info("user Email : " + oAuth2User.getAttribute("email"));

        String email = oAuth2User.getAttribute("email");
        User user = userService.getUserBymail(email);


        if(user == null){
            User currUser = User.builder()
                    .email(email)
                    .name(oAuth2User.getAttribute("name"))
                    .build();


            userService.saveUser(currUser);
            String accessToken = jwtService.generateAccessToken(currUser);
            String refreshToken = jwtService.generateRefreshToken(currUser);

            Cookie cookie = new Cookie("refreshToken", refreshToken);
            cookie.setHttpOnly(true);

            response.addCookie(cookie);

            String frontendUrl = "http://localhost:8080/home.html?token="+accessToken;

            getRedirectStrategy().sendRedirect(request,response,frontendUrl);
        }
    }
}

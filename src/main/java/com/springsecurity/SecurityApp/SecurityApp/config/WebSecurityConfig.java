package com.springsecurity.SecurityApp.SecurityApp.config;

import com.springsecurity.SecurityApp.SecurityApp.entities.enums.Permission;
import com.springsecurity.SecurityApp.SecurityApp.filters.JwtAuthFilter;
import com.springsecurity.SecurityApp.SecurityApp.handler.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final String[] publicRoutes = {
            "/auth/*", "/home.html", "/error"
    };

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{

        httpSecurity
                .authorizeHttpRequests(auth -> {
                  auth
                          .requestMatchers(publicRoutes).permitAll()
                          .requestMatchers(HttpMethod.GET, "/posts/**")
                          .hasAnyRole(Permission.POST_VIEW.name())
                          .requestMatchers(HttpMethod.POST, "/posts/**")
                          .hasAnyRole(Permission.POST_CREATE.name())
                          .requestMatchers(HttpMethod.PUT, "/posts/**")
                          .hasAnyRole(Permission.POST_UPDATE.name())
                          .requestMatchers(HttpMethod.DELETE, "/posts/**")
                          .hasAnyRole(Permission.POST_DELETE.name())
                          .anyRequest().authenticated();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(httpSecurityOAuth2LoginConfigurer ->
                        httpSecurityOAuth2LoginConfigurer
                                .failureUrl("/posts")
                                .successHandler(oAuth2SuccessHandler)

                );

        return httpSecurity.build();
    }

//    @Bean
//    public UserDetailsService myInMemoryUserDetailService(){
//
//        UserDetails userDetails = User.
//                withUsername("kzr")
//                .password(passwordEncoder().encode("passkey"))
//                .roles("USER")
//                .build();
//
//        UserDetails adminUser = User
//                .withUsername("admin")
//                .password(passwordEncoder().encode("admin"))
//                .roles("ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(userDetails,adminUser);
//    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}

package com.springsecurity.SecurityApp.SecurityApp.dto;


import com.springsecurity.SecurityApp.SecurityApp.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

    private String title;

    private String link;

    private String description;

    private LocalDateTime time;

    private UserDto author;
}

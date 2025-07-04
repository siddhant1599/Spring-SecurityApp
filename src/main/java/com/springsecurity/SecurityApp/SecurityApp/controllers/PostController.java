package com.springsecurity.SecurityApp.SecurityApp.controllers;

import com.springsecurity.SecurityApp.SecurityApp.dto.PostDto;
import com.springsecurity.SecurityApp.SecurityApp.services.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
@Slf4j
public class PostController {

    private final PostService postService;

    @GetMapping
    public List<PostDto> getAllPosts(){
        return postService.getAllPosts();
    }

    @GetMapping("/{id}")
    public PostDto getPostById(@PathVariable Long id){
        return postService.getPostById(id);
    }

    @PostMapping
    public PostDto createNewPost(@RequestBody PostDto postDto){
        return postService.createNewPost(postDto);
    }
}

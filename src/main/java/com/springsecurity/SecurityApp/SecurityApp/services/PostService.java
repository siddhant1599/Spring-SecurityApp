package com.springsecurity.SecurityApp.SecurityApp.services;

import com.springsecurity.SecurityApp.SecurityApp.dto.PostDto;

import java.util.List;

public interface PostService {

    List<PostDto> getAllPosts();

    PostDto createNewPost(PostDto inputPost);

    PostDto getPostById(Long id);

}

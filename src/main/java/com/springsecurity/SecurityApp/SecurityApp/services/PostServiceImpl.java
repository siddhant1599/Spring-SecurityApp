package com.springsecurity.SecurityApp.SecurityApp.services;

import com.springsecurity.SecurityApp.SecurityApp.dto.PostDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.UserDto;
import com.springsecurity.SecurityApp.SecurityApp.entities.PostEntity;
import com.springsecurity.SecurityApp.SecurityApp.entities.User;
import com.springsecurity.SecurityApp.SecurityApp.exceptions.ResourceNotFoundException;
import com.springsecurity.SecurityApp.SecurityApp.repositories.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public List<PostDto> getAllPosts() {
       List<PostEntity> postEntities = postRepository.findAll();
       return postEntities.stream().map(postEntity -> {
           return modelMapper.map(postEntity, PostDto.class);
       }).collect(Collectors.toList());
    }

    @Override
    public PostDto createNewPost(PostDto inputPost) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        inputPost.setAuthor(modelMapper.map(user, UserDto.class));
        PostEntity postEntity = postRepository.save(modelMapper.map(inputPost, PostEntity.class));
        return modelMapper.map(postEntity, PostDto.class);
    }

    @Override
    public PostDto getPostById(Long id) {
        User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info("User details : {}", user.toString());
        return modelMapper.map(postRepository.findById(id).orElseThrow(() ->new ResourceNotFoundException("Post not found by id -" +  id))
                , PostDto.class);
    }

}

package com.springsecurity.SecurityApp.SecurityApp.services;

import com.springsecurity.SecurityApp.SecurityApp.dto.SignUpDto;
import com.springsecurity.SecurityApp.SecurityApp.dto.UserDto;
import com.springsecurity.SecurityApp.SecurityApp.entities.User;
import com.springsecurity.SecurityApp.SecurityApp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new BadCredentialsException("User not found by email -" + username));
    }

    public UserDto signUpUser(SignUpDto signUpDto) {
        Optional<User> user = userRepository.findByEmail(signUpDto.getEmail());

        if(user.isPresent()){
            throw new BadCredentialsException("User already exists with email - " + signUpDto.getEmail());
        }

        User toBeCreatedUser = modelMapper.map(signUpDto , User.class);
        toBeCreatedUser.setPassword(passwordEncoder.encode(toBeCreatedUser.getPassword()));
        return modelMapper.map(userRepository.save(toBeCreatedUser), UserDto.class);
    }

    public User getUserById(Long userId){
        return userRepository.findById(userId).orElseThrow(() -> new BadCredentialsException("User not found by userId -" + userId));
    }

}

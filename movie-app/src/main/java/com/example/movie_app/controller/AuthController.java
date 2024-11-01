package com.example.movie_app.controller;

import com.example.movie_app.DTO.LoginDto;
import com.example.movie_app.DTO.RegisterDto;
import com.example.movie_app.entity.User;
import com.example.movie_app.service.CustomUserDetailsService;
import com.example.movie_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private UserService userService;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public AuthController(UserService userService,
                          AuthenticationManager authenticationManager,
                          CustomUserDetailsService userDetailsService
    ){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
    }

    @PostMapping("/register")
    public User register(@RequestBody RegisterDto registerDto){
        return userService.register(registerDto);
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginDto user) {
        return userService.verify(user);
    }

}

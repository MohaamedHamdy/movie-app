package com.example.movie_app.service;

import com.example.movie_app.DTO.LoginDto;
import com.example.movie_app.DTO.RegisterDto;
import com.example.movie_app.config.JWTService;
import com.example.movie_app.entity.Role;
import com.example.movie_app.entity.User;
import com.example.movie_app.repository.RoleRepository;
import com.example.movie_app.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationManager authManager;
    @Autowired
    private JWTService jwtService;
    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public User register(RegisterDto registerDto) {
        Boolean isExist = userRepository.existsUserByUsername(registerDto.getUserName());

        if (isExist) {
            throw new RuntimeException("This username already exists.");
        }
        User user = new User();

        user.setUsername(registerDto.getUserName());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        List<String> roleList;
        List<Role> roles = new ArrayList<>();
        roleList = registerDto.getRole();
        System.out.println(roleList);
        for(String roleName : roleList){
            Role role = roleRepository.findByRoleName(roleName);
            roles.add(role);
        }
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public String verify(LoginDto user) {
        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(user.getUserName());
        } else {
            return "fail";
        }
    }
}

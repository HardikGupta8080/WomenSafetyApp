package com.iiitu.WomenSecurity.controller;



import com.iiitu.WomenSecurity.dto.UserRegistrationDTO;
import com.iiitu.WomenSecurity.dto.UserLoginDTO;
import com.iiitu.WomenSecurity.exception.ResourceNotFoundException;
import com.iiitu.WomenSecurity.jwt.JwtService;
import com.iiitu.WomenSecurity.model.User;
import com.iiitu.WomenSecurity.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository repository;
    private final JwtService jwtService;
    private final PasswordEncoder encoder;

    public AuthController(UserRepository repository,
                          JwtService jwtService,
                          PasswordEncoder encoder){
        this.repository=repository;
        this.jwtService=jwtService;
        this.encoder=encoder;
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserRegistrationDTO userDto){
        User user = new User(userDto.getUsername(), encoder.encode(userDto.getPassword()), userDto.getEmail(), userDto.getPhoneNumber());
        repository.save(user);

        Map<String, String> response = new HashMap<>();
        response.put("message", "User registered successfully");
        response.put("username", user.getUsername());
        response.put("email", user.getEmail());
        response.put("phoneNumber", user.getPhoneNumber());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginDTO loginDto){

        User dbUser = repository.findByUsername(loginDto.getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with username: " + loginDto.getUsername()));

        if(encoder.matches(loginDto.getPassword(), dbUser.getPassword())){
            Map<String, String> response = new HashMap<>();
            response.put("token", jwtService.generateToken(dbUser.getUsername()));
            response.put("username", dbUser.getUsername());
            response.put("email", dbUser.getEmail());
            response.put("phoneNumber", dbUser.getPhoneNumber());
            return ResponseEntity.ok(response);
        }

        Map<String, String> response = new HashMap<>();
        response.put("error", "Invalid credentials");
        return new ResponseEntity<>(response, HttpStatus.UNAUTHORIZED);
    }

}

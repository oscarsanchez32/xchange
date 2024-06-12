package com.example.xchange.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.xchange.entity.ApplicationUser;
import com.example.xchange.exception.InvalidInputException;
import com.example.xchange.payload.LoginRequest;
import com.example.xchange.payload.LoginResponse;
import com.example.xchange.payload.RegistrationRequest;
import com.example.xchange.payload.RegistrationResponse;
import com.example.xchange.service.UserService;
import com.example.xchange.utility.JwtUtil;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, UserService userService, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                        loginRequest.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtil.generateToken(authentication);
        return new ResponseEntity<>(new LoginResponse(token), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> registerUser(@Valid @RequestBody RegistrationRequest registrationRequest){
        if(userService.isUsernameTaken(registrationRequest.getUsername())){
            throw new InvalidInputException("That username has been taken.");
        }
        if(userService.isEmailTaken(registrationRequest.getEmail())){
            throw new InvalidInputException("An account already exists with that email.");
        }
        ApplicationUser user = userService.processRegistrationRequest(registrationRequest);
        RegistrationResponse response = new RegistrationResponse(user.getId(), user.getUserName(), user.getFirstName(), user.getLastName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}

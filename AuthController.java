package com.ums.controller;

import com.ums.entity.AppUser;
import com.ums.payload.JwtResponse;
import com.ums.payload.LoginDto;
import com.ums.service.UserService;
import lombok.RequiredArgsConstructor;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToFile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.ums.payload.UserDto;
//@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/{addUser}")
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto userDto) {
        UserDto dto = userService.addUser(userDto);
        return new ResponseEntity<>(dto ,HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        String Token = userService.verifyLogin(loginDto);
        if (Token!=null) {
            JwtResponse response = new JwtResponse();
            response.setToken(Token);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
            return new ResponseEntity<>("Invalid credentials", HttpStatus.UNAUTHORIZED);
        }
        @GetMapping("/profile")
    public AppUser getCurrentProfile(@AuthenticationPrincipal AppUser user){
        return user;
        }
    }

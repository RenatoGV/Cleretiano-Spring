package com.rengv.cleretiano.controller;

import com.rengv.cleretiano.dto.LoginRequestDTO;
import com.rengv.cleretiano.dto.LoginResponseDTO;
import com.rengv.cleretiano.service.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public LoginResponseDTO login(@Valid @RequestBody LoginRequestDTO request) {
        return userService.login(request);
    }
}

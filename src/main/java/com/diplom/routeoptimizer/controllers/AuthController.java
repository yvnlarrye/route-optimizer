package com.diplom.routeoptimizer.controllers;

import com.diplom.routeoptimizer.dto.JwtAuthenticationResponse;
import com.diplom.routeoptimizer.dto.SignInRequest;
import com.diplom.routeoptimizer.dto.SignUpRequest;
import com.diplom.routeoptimizer.exceptions.InvalidAuthDataException;
import com.diplom.routeoptimizer.services.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/sign-up")
    public ResponseEntity<JwtAuthenticationResponse> signUp(@RequestBody @Valid SignUpRequest request) {
        return new ResponseEntity<>(authenticationService.signUp(request), HttpStatus.CREATED);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationResponse> signIn(@RequestBody @Valid SignInRequest request) throws InvalidAuthDataException {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout() {
        authenticationService.logout();
        return ResponseEntity.ok(Map.of("message", "ok"));
    }

}
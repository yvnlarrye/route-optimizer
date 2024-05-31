package com.diplom.routeoptimizer.services;

import com.diplom.routeoptimizer.dto.JwtAuthenticationResponse;
import com.diplom.routeoptimizer.dto.SignInRequest;
import com.diplom.routeoptimizer.dto.SignUpRequest;
import com.diplom.routeoptimizer.exceptions.InvalidSignInDataException;
import com.diplom.routeoptimizer.exceptions.UserAlreadyExistsException;
import com.diplom.routeoptimizer.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {

        if (userService.isUserExists(request.getUsername())) {
            throw new UserAlreadyExistsException("Пользователь с таким email уже существует");
        }

        User user = userService.create(new User(
                request.getUsername(),
                passwordEncoder.encode(request.getPassword())
        ));

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) throws InvalidSignInDataException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            ));
            var userDetails = userService
                    .userDetailsService()
                    .loadUserByUsername(request.getUsername());

            var jwt = jwtService.generateToken(userDetails);
            return new JwtAuthenticationResponse(jwt);
        } catch (Exception e) {
            throw new InvalidSignInDataException("Неверное имя пользователя или пароль");
        }
    }
}

package com.hackaton.hackton.service.impl;

import com.hackaton.hackton.model.entity.UserEntity;
import com.hackaton.hackton.model.dto.AuthenticationRequest;
import com.hackaton.hackton.model.dto.LoginResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {
    private AuthenticationManager authenticationManager;
    private JwtTokenService tokenService;

    public LoginResponse login(AuthenticationRequest request) {
        try {
            var auth = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.login(), request.password()));

            var token = tokenService.generateToken(((UserEntity) auth.getPrincipal()));

            return LoginResponse.builder().token(token).build();
        } catch (Exception e) {
            log.error("Error on login user {}. Message {}", request.login(), e.getMessage());
            throw e;
        }

    }
}

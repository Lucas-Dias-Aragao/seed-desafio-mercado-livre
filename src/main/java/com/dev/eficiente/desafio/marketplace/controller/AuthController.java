package com.dev.eficiente.desafio.marketplace.controller;

import com.dev.eficiente.desafio.marketplace.config.security.TokenService;
import com.dev.eficiente.desafio.marketplace.model.vo.LoginRequestVo;
import com.dev.eficiente.desafio.marketplace.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authManager;
    private final TokenService tokenService;
    private final CustomUserDetailsService userDetailsService;

    @PostMapping("/login")
    public String login(@RequestBody LoginRequestVo request) {
        authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()
                )
        );

        UserDetails user = userDetailsService.loadUserByUsername(request.login());
        return tokenService.gerarToken(user);
    }

}

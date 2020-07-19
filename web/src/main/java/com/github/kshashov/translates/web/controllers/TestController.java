package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.data.repos.UsersRepository;
import com.github.kshashov.translates.web.security.TokenProvider;
import com.github.kshashov.translates.web.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("test")
@RequestMapping("/test")
public class TestController {
    private final UsersRepository usersRepository;
    private final TokenProvider tokenProvider;

    @Autowired
    public TestController(UsersRepository usersRepository, TokenProvider tokenProvider) {
        this.usersRepository = usersRepository;
        this.tokenProvider = tokenProvider;
    }

    @GetMapping("/token")
    public String token() {
        UserPrincipal userPrincipal = () -> usersRepository.findById(0L).get();
        String token = tokenProvider.createToken(new UsernamePasswordAuthenticationToken(userPrincipal, null));
        return token;
    }
}

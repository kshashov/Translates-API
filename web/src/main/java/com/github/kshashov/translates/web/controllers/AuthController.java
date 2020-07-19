package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.OAuthClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/oauthClients")
public class AuthController {
    private final ClientRegistrationRepository clientRegistrationRepository;

    @Autowired
    public AuthController(ClientRegistrationRepository clientRegistrationRepository) {
        this.clientRegistrationRepository = clientRegistrationRepository;
    }

    @GetMapping
    public List<OAuthClient> getClients() {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository).as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }

        List<OAuthClient> clients = new ArrayList<>();
        if (clientRegistrations == null) {
            return clients;
        }

        clientRegistrations.forEach(registration -> {
            OAuthClient client = new OAuthClient(
                    registration.getClientName(),
                    "/oauth2/authorize/" + registration.getRegistrationId());
            clients.add(client);
        });

        return clients;
    }
}

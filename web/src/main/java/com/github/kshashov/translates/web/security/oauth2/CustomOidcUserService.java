package com.github.kshashov.translates.web.security.oauth2;

import com.github.kshashov.translates.data.entities.User;
import com.github.kshashov.translates.data.services.UsersAdminService;
import com.github.kshashov.translates.web.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class CustomOidcUserService extends OidcUserService {
    private final UsersAdminService usersService;

    @Autowired
    public CustomOidcUserService(UsersAdminService usersService) {
        this.usersService = usersService;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest userRequest) throws OAuth2AuthenticationException {
        OidcUser oidcUser = super.loadUser(userRequest);
        // TODO populate authorities
        String clientName = userRequest.getClientRegistration().getClientName().toLowerCase();
        User user = updateGoogleUser(clientName, oidcUser.getAttributes());
        return new CustomUser(oidcUser, user);
    }

    private User updateGoogleUser(String clientName, Map attributes) {
        var sub = (String) attributes.getOrDefault("sub", "");
        var email = (String) attributes.getOrDefault("email", "");
        var name = (String) attributes.getOrDefault("name", "");
        if (name.length() < 3) {
            name = "NoName";
        }
//        var picture = (String) attributes.get("picture");
        UsersAdminService.CreateUserInfo userInfo = new UsersAdminService.CreateUserInfo();
        userInfo.setName(name);
        userInfo.setEmail(email);
        userInfo.setSub(sub);
        userInfo.setClient(clientName);

        User user = usersService.getOrCreateUser(userInfo);
        return user;
    }

    private class CustomUser implements OidcUser, UserPrincipal {

        private final OidcUser oidcUser;
        private final User user;

        public CustomUser(OidcUser oidcUser, User user) {
            this.oidcUser = oidcUser;
            this.user = user;
        }

        @Override
        public User getUser() {
            return user;
        }

        @Override
        public Map<String, Object> getClaims() {
            return oidcUser.getClaims();
        }

        @Override
        public OidcUserInfo getUserInfo() {
            return oidcUser.getUserInfo();
        }

        @Override
        public OidcIdToken getIdToken() {
            return oidcUser.getIdToken();
        }

        @Override
        public Map<String, Object> getAttributes() {
            return oidcUser.getAttributes();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return oidcUser.getAuthorities();
        }

        @Override
        public String getName() {
            return user.getName();
        }
    }
}

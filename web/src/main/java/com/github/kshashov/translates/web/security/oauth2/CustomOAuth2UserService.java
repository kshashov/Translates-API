package com.github.kshashov.translates.web.security.oauth2;

import com.github.kshashov.translates.data.entities.User;
import com.github.kshashov.translates.data.services.UsersAdminService;
import com.github.kshashov.translates.web.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {
    private final UsersAdminService usersService;

    @Autowired
    public CustomOAuth2UserService(UsersAdminService usersService) {
        this.usersService = usersService;
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User auth2User = super.loadUser(userRequest);
        // TODO populate authorities?
        String clientName = userRequest.getClientRegistration().getClientName().toLowerCase();
        User user = updateOAuth2User(clientName, auth2User.getAttributes());
        return new CustomUser(auth2User, user);
    }

    private User updateOAuth2User(String clientName, Map attributes) {
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

    private class CustomUser implements OAuth2User, UserPrincipal {

        private final OAuth2User auth2User;
        private final User user;

        public CustomUser(OAuth2User auth2User, User user) {
            this.auth2User = auth2User;
            this.user = user;
        }

        @Override
        public User getUser() {
            return user;
        }

        @Override
        public Map<String, Object> getAttributes() {
            return auth2User.getAttributes();
        }

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return auth2User.getAuthorities();
        }

        @Override
        public String getName() {
            return user.getName();
        }
    }
}

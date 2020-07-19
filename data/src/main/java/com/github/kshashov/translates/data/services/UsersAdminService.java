package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.data.entities.User;
import lombok.Getter;
import lombok.Setter;

public interface UsersAdminService {
    User getOrCreateUser(CreateUserInfo createUserInfo);

    @Getter
    @Setter
    class CreateUserInfo {
        private String name;
        private String email;
        private String client;
        private String sub;
    }
}

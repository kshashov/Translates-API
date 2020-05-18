package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.data.entities.User;

public interface UsersAdminService {
    User getOrCreateUser(String email, String name);
}

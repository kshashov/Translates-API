package com.github.kshashov.translates.web.security;

import com.github.kshashov.translates.data.entities.User;

public interface UserPrincipal {
    User getUser();
}

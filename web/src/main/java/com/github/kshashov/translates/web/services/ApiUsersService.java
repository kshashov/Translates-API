package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.web.dto.CurrentUser;
import com.github.kshashov.translates.web.dto.Paged;
import com.github.kshashov.translates.web.dto.User;
import com.github.kshashov.translates.web.dto.UserInfo;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ApiUsersService {
    Optional<CurrentUser> getCurrentUser();

    Optional<User> getUser(Long userId);

    List<User> getUsers();

    Paged<User> getUsers(Pageable pageable, String filter);

    User updateUser(Long userId, UserInfo userInfo);

    User updateUserRole(Long userId, Long roleId);
}

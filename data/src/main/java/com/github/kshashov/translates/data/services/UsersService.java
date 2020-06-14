package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.data.entities.User;
import lombok.Getter;
import lombok.Setter;

public interface UsersService {
    User updateUser(Long userId, UserInfo userInfo);

    User updateUserRole(Long userId, Long roleId);

    @Getter
    @Setter
    public class UserInfo {
        String name;
    }
}

package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.data.entities.Role;
import com.github.kshashov.translates.data.entities.User;
import com.github.kshashov.translates.data.repos.RolesRepository;
import com.github.kshashov.translates.data.repos.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
public class UsersServiceImpl implements UsersService {
    private final UsersRepository usersRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public UsersServiceImpl(UsersRepository usersRepository, RolesRepository rolesRepository) {
        this.usersRepository = usersRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public User updateUser(Long userId, UserInfo userInfo) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(userInfo);

        if (StringUtils.isBlank(userInfo.getName())) {
            throw new BadRequestException("User name is empty");
        }

        User user = usersRepository.getOne(userId);
        user.setName(userInfo.getName());
        user = usersRepository.save(user);

        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public User updateUserRole(Long userId, Long roleId) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(roleId);

        Role role = rolesRepository.getOne(roleId);

        User user = usersRepository.getOne(userId);
        user.setRole(role);
        user = usersRepository.save(user);

        return user;
    }
}

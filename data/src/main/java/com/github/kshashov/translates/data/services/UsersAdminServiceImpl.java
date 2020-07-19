package com.github.kshashov.translates.data.services;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.data.entities.Role;
import com.github.kshashov.translates.data.entities.User;
import com.github.kshashov.translates.data.enums.RoleType;
import com.github.kshashov.translates.data.repos.RolesRepository;
import com.github.kshashov.translates.data.repos.UsersRepository;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
public class UsersAdminServiceImpl implements UsersAdminService {

    private final RolesRepository rolesRepository;
    private final UsersRepository usersRepository;
    private final Role userRole;
    private final Role adminRole;

    @Autowired
    public UsersAdminServiceImpl(RolesRepository rolesRepository, UsersRepository usersRepository) {
        this.rolesRepository = rolesRepository;
        this.usersRepository = usersRepository;

        this.userRole = rolesRepository.findOneByCode(RoleType.USER.getCode()).get();
        this.adminRole = rolesRepository.findOneByCode(RoleType.ADMIN.getCode()).get();
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRED)
    public User getOrCreateUser(CreateUserInfo userInfo) {
        Objects.requireNonNull(userInfo);

        Optional<User> user = usersRepository.findOneByClientAndSub(userInfo.getClient(), userInfo.getSub());

        return user.orElseGet(() -> {
            try {
                return createUser(userInfo);
            } catch (javax.validation.ConstraintViolationException ex) {
                throw new BadRequestException("Invalid request", ex);
            } catch (DataIntegrityViolationException ex) {
                if (ex.getCause() instanceof ConstraintViolationException) {
                    ConstraintViolationException casted = (ConstraintViolationException) ex.getCause();
                    if ("users_unique_client_sub".equals(casted.getConstraintName())) {
                        throw new BadRequestException("User already exists", ex);
                    } else {
                        throw new BadRequestException("Invalid request", ex);
                    }
                }
                throw ex;
            }
        });
    }

    private User createUser(CreateUserInfo userInfo) {
        Objects.requireNonNull(userInfo);

        if (StringUtils.isBlank(userInfo.getClient())) {
            throw new BadRequestException("OAuth Client is empty");
        }

        if (StringUtils.isBlank(userInfo.getSub())) {
            throw new BadRequestException("User Id is empty");
        }

        if (StringUtils.isBlank(userInfo.getName())) {
            throw new BadRequestException("Name is empty");
        }

        User user = new User();
        user.setName(userInfo.getName());
        user.setEmail(userInfo.getEmail());
        user.setSub(userInfo.getSub());
        user.setClient(userInfo.getClient());

        // First user = admin, else = user
        Role role = usersRepository.count() == 0
                ? adminRole
                : userRole;

        user.setRole(role);

        return usersRepository.save(user);
    }
}

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
    public User getOrCreateUser(String email, String name) {
        Optional<User> user = usersRepository.findOneByEmail(email);

        return user.orElseGet(() -> {
            try {
                return createUser(email, name);
            } catch (javax.validation.ConstraintViolationException ex) {
                throw new BadRequestException("Invalid request", ex);
            } catch (DataIntegrityViolationException ex) {
                if (ex.getCause() instanceof ConstraintViolationException) {
                    ConstraintViolationException casted = (ConstraintViolationException) ex.getCause();
                    if ("users_unique_email".equals(casted.getConstraintName())) {
                        throw new BadRequestException("User " + email + " already exists", ex);
                    } else {
                        throw new BadRequestException("Invalid request", ex);
                    }
                }
                throw ex;
            }
        });
    }

    private User createUser(String email, String name) {
        // Check email only
        if (StringUtils.isBlank(email)) {
            throw new BadRequestException("Email is empty");
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);

        // First user = admin, else = user
        Role role = usersRepository.count() == 0
                ? adminRole
                : userRole;

        user.setRole(role);

        return usersRepository.save(user);
    }
}

package com.github.kshashov.translates.web.services;

import com.github.kshashov.translates.common.errors.BadRequestException;
import com.github.kshashov.translates.common.errors.NotFoundException;
import com.github.kshashov.translates.data.entities.Permission;
import com.github.kshashov.translates.data.repos.PermissionsRepository;
import com.github.kshashov.translates.data.repos.RolesRepository;
import com.github.kshashov.translates.data.repos.UsersRepository;
import com.github.kshashov.translates.data.services.UsersService;
import com.github.kshashov.translates.web.dto.CurrentUser;
import com.github.kshashov.translates.web.dto.Paged;
import com.github.kshashov.translates.web.dto.User;
import com.github.kshashov.translates.web.dto.UserInfo;
import com.github.kshashov.translates.web.security.SecurityUtils;
import com.github.kshashov.translates.web.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ApiUsersServiceImpl implements ApiUsersService {
    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final PermissionsRepository permissionsRepository;
    private final RolesRepository rolesRepository;

    @Autowired
    public ApiUsersServiceImpl(UsersService usersService, UsersRepository usersRepository, PermissionsRepository permissionsRepository, RolesRepository rolesRepository) {
        this.usersService = usersService;
        this.usersRepository = usersRepository;
        this.permissionsRepository = permissionsRepository;
        this.rolesRepository = rolesRepository;
    }

    @Override
    @PreAuthorize("isAuthenticated()")
    public Optional<CurrentUser> getCurrentUser() {
        return SecurityUtils.getCurrentUser()
                .map(UserPrincipal::getUser)
                .map(user -> {
                    List<String> permisions = rolesRepository.findOneByCode(user.getRole().getCode())
                            .map(r -> r.getPermissions().stream().map(Permission::getCode).collect(Collectors.toList()))
                            .orElse(new ArrayList<>());

                    return new CurrentUser(User.of(user), permisions);
                });
    }

    @PreAuthorize("(isAuthenticated() && (#userId == authentication.principal.user.id)) " +
            "or hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_USERS.getCode())")
    public Optional<User> getUser(Long userId) {
        Objects.requireNonNull(userId);

        return usersRepository.findById(userId)
                .map(User::of);
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_USERS.getCode())")
    public List<User> getUsers() {
        return usersRepository.findAll().stream()
                .map(User::of)
                .collect(Collectors.toList());
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_USERS.getCode())")
    public Paged<User> getUsers(Pageable pageable, String filter) {
        Specification<com.github.kshashov.translates.data.entities.User> spec = (Specification<com.github.kshashov.translates.data.entities.User>) (root, query, criteriaBuilder) -> {
            Predicate nameLike = criteriaBuilder
                    .like(
                            criteriaBuilder.upper(root.get("name")),
                            "%" + filter.toUpperCase() + "%");
            Predicate emailLike = criteriaBuilder
                    .like(
                            criteriaBuilder.upper(root.get("email")),
                            "%" + filter.toUpperCase() + "%");

            return criteriaBuilder.or(nameLike, emailLike);
        };

        Page<com.github.kshashov.translates.data.entities.User> page = usersRepository.findAll(spec, pageable);
        Paged<User> paged = Paged.of(page, User::of);
        return paged;
    }

    @Override
    @PreAuthorize("isAuthenticated() && (#userId == authentication.principal.user.id)")
    public User updateUser(Long userId, UserInfo userInfo) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(userInfo);

        if (!usersRepository.existsById(userId)) {
            throw new NotFoundException("User is not found");
        }

        UsersService.UserInfo userInfo1 = new UsersService.UserInfo();
        userInfo1.setName(userInfo.getName());
        return User.of(usersService.updateUser(userId, userInfo1));
    }

    @Override
    @PreAuthorize("hasAuthority(T(com.github.kshashov.translates.data.enums.PermissionType).MANAGE_USERS.getCode())")
    public User updateUserRole(Long userId, Long roleId) {
        Objects.requireNonNull(userId);
        Objects.requireNonNull(roleId);

        if (!usersRepository.existsById(userId)) {
            throw new NotFoundException("User is not found");
        }

        if (!rolesRepository.existsById(roleId)) {
            throw new BadRequestException("Role is not found");
        }

        return User.of(usersService.updateUserRole(userId, roleId));
    }
}

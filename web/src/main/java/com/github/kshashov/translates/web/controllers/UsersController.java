package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.*;
import com.github.kshashov.translates.web.services.ApiUsersService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UsersController {
    private final ApiUsersService userService;

    @Autowired
    public UsersController(ApiUsersService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<CurrentUser> getCurrentUser() {
        Optional<CurrentUser> user = userService.getCurrentUser();
        return ResponseEntity.of(user);
    }

    @GetMapping("/{userId}")
    @SecurityRequirement(name = "bearer")
    public ResponseEntity<User> getUser(@PathVariable("userId") long userId) {
        Optional<User> user = userService.getUser(userId);
        return ResponseEntity.of(user);
    }

    @PostMapping("/{userId}")
    @SecurityRequirement(name = "bearer")
    public User updateUser(@PathVariable("userId") long userId, @Valid @RequestBody UserInfo userInfo) {
        return userService.updateUser(userId, userInfo);
    }

    @PostMapping("/{userId}/role")
    @SecurityRequirement(name = "bearer")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("userId") long userId, @Valid @RequestBody UserRoleInfo userRoleInfo) {
        userService.updateUserRole(userId, userRoleInfo.getId());
    }

    @GetMapping("")
    @SecurityRequirement(name = "bearer")
    public Paged<User> getUsers(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam(value = "sort", required = false) String sort,
            @RequestParam(value = "direction", required = false) String direction
    ) throws InterruptedException {
        Thread.sleep(1000);
        if (StringUtils.isBlank(sort)) {
            sort = "name";
        }
        if (StringUtils.isBlank(direction)) {
            direction = "asc";
        }
        if (size < 1) {
            size = Integer.MAX_VALUE;
        }

        return userService.getUsers(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)), filter);
    }
}

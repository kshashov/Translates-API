package com.github.kshashov.translates.web.controllers;

import com.github.kshashov.translates.web.dto.*;
import com.github.kshashov.translates.web.services.SecuredUsersService;
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
    private final SecuredUsersService userService;

    @Autowired
    public UsersController(SecuredUsersService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<CurrentUser> getCurrentUser() {
        Optional<CurrentUser> user = userService.getCurrentUser();
        return ResponseEntity.of(user);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getCurrentUser(@PathVariable("userId") long userId) {
        Optional<User> user = userService.getUser(userId);
        return ResponseEntity.of(user);
    }

    @PostMapping("/{userId}")
    public User updateUser(@PathVariable("userId") long userId, @Valid @RequestBody UserInfo userInfo) {
        return userService.updateUser(userId, userInfo);
    }

    @PostMapping("/{userId}/role")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("userId") long userId, @Valid @RequestBody UserRoleInfo userRoleInfo) {
        userService.updateUserRole(userId, userRoleInfo.getId());
    }

    @GetMapping("/")
    public Paged<User> getUsers(@RequestParam("page") int page, @RequestParam("size") int size, @RequestParam("filter") String filter, @RequestParam("sort") String sort, @RequestParam("direction") String direction) throws InterruptedException {
        Thread.sleep(2000);
        if (StringUtils.isBlank(sort)) {
            sort = "name";
        }
        if (StringUtils.isBlank(direction)) {
            direction = "asc";
        }

        return userService.getUsers(PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(direction), sort)), filter);
    }
}

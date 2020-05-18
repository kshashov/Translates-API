package com.github.kshashov.translates.web.security;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public class SecurityUtils {

    public static boolean isUserLoggedIn() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            return com.github.kshashov.translates.web.security.SecurityUtils.isUserLoggedIn(context.getAuthentication());
        }
        return false;
    }

    private static boolean isUserLoggedIn(Authentication authentication) {
        return authentication != null
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.getPrincipal() != null
                && authentication.getPrincipal() instanceof UserPrincipal;
    }

    public static Optional<UserPrincipal> getCurrentUser() {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context != null) {
            Authentication auth = context.getAuthentication();
            if (auth != null) {
                Object principal = auth.getPrincipal();
                if (principal instanceof UserPrincipal) {
                    return Optional.of((UserPrincipal) context.getAuthentication().getPrincipal());
                }
            }
        }
        // Anonymous or no authentication.
        return Optional.empty();
    }

    public static boolean hasUser() {
        try {
            return getCurrentUser()
                    .map(p -> (p.getUser() != null))
                    .orElse(false);
        } catch (Exception ex) {
            return false;
        }
    }
}

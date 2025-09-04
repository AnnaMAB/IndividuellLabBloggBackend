package org.example.individuelllabbloggbackend.services;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

public class Utils {


    private static Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private static Jwt getJwt() {
        Authentication auth = getAuth();
        if (auth != null && auth.getPrincipal() instanceof Jwt) {
            return (Jwt) auth.getPrincipal();
        }
        throw new IllegalStateException("No JWT token found in SecurityContext");
    }

    public static String getUserId() {
        return getJwt().getSubject();
    }

    public static String getUsername() {
        return getJwt().getClaimAsString("preferred_username");
    }

    public static boolean isAdmin() {
        Authentication auth = getAuth();
        if (auth == null) {
            return false;
        }
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }
}

package org.example.individuelllabbloggbackend.configs;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class UserInfo {


    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    private Jwt getJwt() {
        Authentication auth = getAuth();
        if (auth != null && auth.getPrincipal() instanceof Jwt) {
            return (Jwt) auth.getPrincipal();
        }
        throw new IllegalStateException("No JWT token found in SecurityContext");
    }

    public String getUserId() {
        return getJwt().getSubject();
    }

    public String getUsername() {
        return getJwt().getClaimAsString("preferred_username");
    }

    public boolean isAdmin() {
        Authentication auth = getAuth();
        if (auth == null) {
            return false;
        }
        return auth.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch("ROLE_ADMIN"::equals);
    }
}

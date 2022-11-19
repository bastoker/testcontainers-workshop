package nl.jnext.workshop.testcontainers.vakantieplanner.controller;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.adapters.springsecurity.account.SimpleKeycloakAccount;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.AccessToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Collection;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class UserInfoController {
    @GetMapping(path = "/userinfo", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('user')")
    public String getUserInfo(KeycloakAuthenticationToken authToken) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        System.out.println(authorities);
        String userIdByToken = "";
        String name = "";

        Principal principal = (Principal) authToken.getPrincipal();
        if (principal instanceof KeycloakPrincipal<?> kcPrincipal) {
            AccessToken token = kcPrincipal.getKeycloakSecurityContext().getToken();
            name = token.getPreferredUsername();
            userIdByToken = token.getId();
        }

        return "{\"name\": \"" + name + "\", \"userIdByToken\": \"" + userIdByToken + "\"}";
    }

    @GetMapping(path = "/superuserinfo", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('superuser')")
    public String getSuperUserInfo(KeycloakAuthenticationToken authToken) {
        String userIdByToken = "";
        String name = "";

        Principal principal = (Principal) authToken.getPrincipal();
        if (principal instanceof KeycloakPrincipal<?> kcPrincipal) {
            AccessToken token = kcPrincipal.getKeycloakSecurityContext().getToken();
            name = token.getPreferredUsername();
            userIdByToken = token.getId();
        }

        return "{\"name\": \"" + name + "\", \"userIdByToken\": \"" + userIdByToken + "\"}";
    }
}

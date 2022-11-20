package nl.jnext.workshop.testcontainers.vakantieplanner.controller.auth;

import org.keycloak.KeycloakPrincipal;
import org.keycloak.representations.AccessToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Function;

@Configuration
public class KeycloakUserNameResolver {
    /**
     * Credits to https://stackoverflow.com/a/60012403
     */
    @Bean
    public Function<KeycloakPrincipal, String> fetchUser() {
        return (principal -> {
            AccessToken token = principal.getKeycloakSecurityContext().getToken();
            return token.getPreferredUsername();
        });
    }
}


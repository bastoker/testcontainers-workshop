package nl.jnext.workshop.testcontainers.vakantieplanner.controller;

import nl.jnext.workshop.testcontainers.vakantieplanner.controller.auth.CurrentUser;
import nl.jnext.workshop.testcontainers.vakantieplanner.controller.exceptions.OverlappingHolidayException;
import nl.jnext.workshop.testcontainers.vakantieplanner.model.Holiday;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
@RestController
@RequestMapping("/holiday")
public class HolidayController extends AbstractController {

    Logger logger = LoggerFactory.getLogger(HolidayController.class);

    @GetMapping(path = "/{user}", produces = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('user')")
    public String getHoliday(
            @CurrentUser String keycloakUser,
            @PathVariable("user") String user) {
        logger.info("Endpoint /holiday/{} is called as keycloak user {}", user, keycloakUser);
        return "{\"keycloakUsername\": \"" + keycloakUser + "\"}";
    }

    @PostMapping(path = "/{user}", consumes = APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAnyRole('user')")
    public void postHoliday(
            @CurrentUser String keycloakUser,
            @PathVariable("user") String user,
            @RequestBody Holiday holiday) {
        logger.info("Endpoint /holiday/{} is called as keycloak user {}", user, keycloakUser);
        checkMatchingUsers(keycloakUser, user, "User {} is not allowed to view holiday of someone else, in this case {}");
        throw new OverlappingHolidayException();
    }
}

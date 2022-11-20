package nl.jnext.workshop.testcontainers.vakantieplanner.controller;

import nl.jnext.workshop.testcontainers.vakantieplanner.controller.exceptions.ForbiddenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AbstractController {
    private static final Logger logger = LoggerFactory.getLogger(HolidayController.class);

    protected static void checkMatchingUsers(String keycloakUser, String user, String message) {
        if (! keycloakUser.equals(user)) {
            logger.warn(message, keycloakUser, user);
            throw new ForbiddenException(); // user can only see own holidays
        }
    }
}

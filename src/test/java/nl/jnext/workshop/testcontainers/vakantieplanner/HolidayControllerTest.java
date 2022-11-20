package nl.jnext.workshop.testcontainers.vakantieplanner;

import io.restassured.http.ContentType;
import nl.jnext.workshop.testcontainers.helper.testcontainer.KeycloakTestcontainer;
import nl.jnext.workshop.testcontainers.vakantieplanner.model.Holiday;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ComponentScan
class HolidayControllerTest {

    Logger logger = LoggerFactory.getLogger(HolidayControllerTest.class);

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15"))
            .withDatabaseName("integration-tests-db")
            .withUsername("admin")
            .withPassword("@dm1n");

    // Default admin username/password is admin/admin
    static KeycloakTestcontainer keycloak = new KeycloakTestcontainer();

    @LocalServerPort
    protected int localServerPort;

    @DynamicPropertySource
    static void configureApplicationContext(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("keycloak.auth-server-url", keycloak::getAuthServerUrl);
    }

    @BeforeAll
    static void setup() {
        postgres.start();
        keycloak.start();
    }

    @Test
    void contextLoads() {
    }

    @Test
    void retrieveHolidaysForUser() {
        // First add the user
        String id = keycloak.addNormalUser("alice", "secret");

        // Now try to login as this user
        String bearerToken = keycloak.getBearerTokenFor("alice", "secret");

        // Call our API using this bearer token:
        given().headers(
                "Authorization",
                "Bearer " + bearerToken).
        when().
                get(String.format("http://localhost:%d/holiday/alice", localServerPort)).
        then().
                statusCode(200).
                body("keycloakUsername", equalTo("alice"));
    }

    @Test
    void addingHolidayThatConflictsWithExistingHolidayIsRejected() {
        // First add the user
        String id = keycloak.addNormalUser("bob", "secret");
        // Now try to login as this user
        String bearerToken = keycloak.getBearerTokenFor("bob", "secret");

        // Holiday to post:
        Holiday holiday = new Holiday(
                "Zomervakantie",
                LocalDate.of(2022, 8, 11),
                LocalDate.of(2022, 8, 30)
        );

        // Call our API using this bearer token:
        given().
                headers(
                        "Authorization",
                        "Bearer " + bearerToken).
                contentType(ContentType.JSON).
                body(holiday).
                when().
                post(String.format("http://localhost:%d/holiday/bob", localServerPort)).
                then().
                log().all().
                statusCode(200).
                body("keycloakUsername", equalTo("bob"));
    }
}

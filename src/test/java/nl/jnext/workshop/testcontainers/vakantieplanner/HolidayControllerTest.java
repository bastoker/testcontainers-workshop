package nl.jnext.workshop.testcontainers.vakantieplanner;

import helper.KeycloakTestcontainer;
import io.restassured.http.ContentType;
import nl.jnext.workshop.testcontainers.vakantieplanner.dao.VakantieRepository;
import nl.jnext.workshop.testcontainers.vakantieplanner.model.Holiday;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(
        webEnvironment = WebEnvironment.RANDOM_PORT,
        properties = {})
@ComponentScan
class HolidayControllerTest {

    Logger logger = LoggerFactory.getLogger(HolidayControllerTest.class);

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15"))
            .withDatabaseName("integration-tests-db")
            .withUsername("admin")
            .withPassword("admin");

    // Default admin username/password is admin/admin
    static KeycloakTestcontainer keycloak = new KeycloakTestcontainer();

    @LocalServerPort
    protected int localServerPort;

    @Autowired
    VakantieRepository vakantieRepository;

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
    @DisplayName("Een ingelogde gebruiker kan zijn vakanties opvragen")
    void retrieveHolidaysForUser() {
        // First add the user
        String id = keycloak.addNormalUser("sam", "secret");

        // Now try to login as this user
        String bearerToken = keycloak.getBearerTokenFor("sam", "secret");

        // Voeg een vakantie toe en bewaar het technische id
        int idOfHoliday = vakantieRepository.addHoliday("sam", new Holiday(
                -1,
                "Zomervakantie",
                LocalDate.of(2022, 7, 2),
                LocalDate.of(2022, 7, 22)
        )).id();

        // Call our API using this bearer token:
        given()
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken)
                .when()
                .get(String.format("http://localhost:%d/holiday/sam", localServerPort))
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("findAll{i -> i.id == %s && i.description == \"%s\"}".formatted(idOfHoliday, "Zomervakantie"), not(empty()))
        ;
    }

    @DisplayName("Een vakantie toevoegen kan niet als deze overlapt met een bestaande vakantie")
    @Test
    void addingHolidayThatConflictsWithExistingHolidayIsRejected() {
        // First add the user
        String id = keycloak.addNormalUser("bob", "secret");
        // Now try to login as this user
        String bearerToken = keycloak.getBearerTokenFor("bob", "secret");

        // Holiday to post:
        Holiday holiday = new Holiday(
                123,
                "Zomervakantie",
                LocalDate.of(2022, 8, 11),
                LocalDate.of(2022, 8, 30)
        );

        // Call our API using this bearer token:
        given()
                .headers(
                        "Authorization",
                        "Bearer " + bearerToken)
                .contentType(ContentType.JSON)
                .body(holiday)
                .when()
                .post(String.format("http://localhost:%d/holiday/bob", localServerPort))
                .then()
                .log().ifValidationFails()
                .statusCode(200).
                body("keycloakUsername", equalTo("bob"));
    }
}

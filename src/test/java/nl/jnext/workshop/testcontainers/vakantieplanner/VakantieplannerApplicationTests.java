package nl.jnext.workshop.testcontainers.vakantieplanner;

import nl.jnext.workshop.testcontainers.helper.KeycloakTestcontainer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class VakantieplannerApplicationTests {

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
    @DisplayName("Call to API without bearer token fails")
    void loginWithoutTokenFails() {
        when().
                get(String.format("http://localhost:%d/userinfo", localServerPort)).
                then().
                statusCode(401);
    }

    @Test
    @DisplayName("First login as a normal user adds user to application database")
    void firstLoginAsNormalUser() {
        // First add the user
        String id = keycloak.addNormalUser("bob", "secret");
        System.out.println(id);
        keycloak.showUsersInKeycloak();

        // Now try to login as this user
        String bearerToken = keycloak.getBearerTokenFor("bob", "secret");

        System.out.println(String.format("http://localhost:%d/userinfo", localServerPort));

        // Call our API using this bearer token:
        given().headers(
                "Authorization",
                "Bearer " + bearerToken).
        when().
                get(String.format("http://localhost:%d/userinfo", localServerPort)).
        then().
                statusCode(200).
                body("name", equalTo("bob"));
    }
}

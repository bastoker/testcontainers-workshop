package nl.jnext.workshop.testcontainers.vakantieplanner;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class VakantieplannerApplicationTests {

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
			DockerImageName.parse("postgres:15"));

	@DynamicPropertySource
	static void redisProperties(DynamicPropertyRegistry registry) {
		postgres.withDatabaseName("integration-tests-db")
				.withUsername("admin")
				.withPassword("adm1n");
		postgres.start();
		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}
	@BeforeAll
	static void setup() {

	}

	@Test
	void contextLoads() {
	}

	@Test
	void insertTeamMember() {

	}

}

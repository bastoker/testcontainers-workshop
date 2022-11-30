package standalone;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Scanner;

import static org.testcontainers.containers.PostgreSQLContainer.POSTGRESQL_PORT;

public class StartDatabaseStandalone {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15"))
            .withDatabaseName("standalone-db")
            .withUsername("admin")
            .withPassword("admin");

    public static void main(String... args) {
        postgres.start();

        System.out.println("**** PostgreSQL database is started.");
        System.out.println("**** PostgreSQL is accessible on " + postgres.getJdbcUrl());
        System.out.printf(
                "**** docker run -it psql -h host.docker.internal -p %s -d %s -U %s%n",
                postgres.getMappedPort(POSTGRESQL_PORT),
                postgres.getDatabaseName(),
                postgres.getUsername());
        loopEndlessly();
    }

    private static void loopEndlessly() {
        System.out.println("**** Press q and RETURN to stop the database");
        Scanner userInput = new Scanner(System.in);
        while(!userInput.next().startsWith("q"));
        System.out.println("**** Database server stopped");
    }
}

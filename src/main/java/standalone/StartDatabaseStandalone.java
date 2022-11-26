package standalone;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Scanner;

public class StartDatabaseStandalone {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            DockerImageName.parse("postgres:15"))
            .withDatabaseName("integration-tests-db")
            .withUsername("admin")
            .withPassword("@dm1n");

    public static void main(String... args) {
        postgres.start();

        System.out.println("**** PostgreSQL database is started.");
        System.out.println("**** PostgreSQL is accessible on " + postgres.getJdbcUrl());
        loopEndlessly();
    }

    private static void loopEndlessly() {
        System.out.println("**** Press q and RETURN to stop the database");
        Scanner userInput = new Scanner(System.in);
        while(!userInput.next().startsWith("q"));
        System.out.println("**** Database server stopped");
    }
}

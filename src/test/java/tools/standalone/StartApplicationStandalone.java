package tools.standalone;


import nl.jnext.workshop.testcontainers.vakantieplanner.VakantieplannerApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Scanner;

@SpringBootApplication
public class StartApplicationStandalone {


//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//        registry.add("keycloak.auth-server-url", keycloak::getAuthServerUrl);


    public static void main(String[] args) {
        args = new String[]{"--spring.config.location=classpath:application-standalone.yaml"};
        ConfigurableApplicationContext context = SpringApplication.run(VakantieplannerApplication.class, args);
        loopEndlessly();
    }

    private static void loopEndlessly() {
        System.out.println("**** Press q and RETURN to stop the Vakantieplanner Application");
        Scanner userInput = new Scanner(System.in);
        while (!userInput.next().startsWith("q"));
        System.out.println("**** Vakantieplanner Application stopped");
    }
}

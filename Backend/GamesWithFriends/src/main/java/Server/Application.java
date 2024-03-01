package Server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Sample Spring Boot Application.
 * 
 * @author Markus Roth, Zachary Sears
 */

@SpringBootApplication
@EntityScan(basePackages = {"Server.User", "Server.FriendGroup", "Server.Trophies"})
@EnableJpaRepositories(basePackages = {"Server.User", "Server.FriendGroup", "Server.Trophies"})
public class Application {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

}

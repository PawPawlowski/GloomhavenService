package webapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = "gloomhavenService")
@EntityScan("gloomhavenService")
@EnableJpaRepositories(basePackages = "gloomhavenService.services.repository.beans")
public class GloomhavenServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(GloomhavenServiceApplication.class, args);
    }

}

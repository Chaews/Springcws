package ezenwebcws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing // JPA 매핑된 엔티티의 변화감지 [스프링 시작시 감지기능]
@SpringBootApplication
public class Application {
    public static void main(String[] args) {

        SpringApplication.run( (Application.class) ); }
}

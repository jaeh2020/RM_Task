package hello.rm_task;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "hello.rm_task.domain")
public class RmTaskApplication {

    public static void main(String[] args) {
        SpringApplication.run(RmTaskApplication.class, args);
    }

}

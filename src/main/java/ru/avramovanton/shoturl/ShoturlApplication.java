package ru.avramovanton.shoturl;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShoturlApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShoturlApplication.class, args);
    }

}

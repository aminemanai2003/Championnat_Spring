package tn.esprit.ds.championnat1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Championnat1Application {

    public static void main(String[] args) {
        SpringApplication.run(Championnat1Application.class, args);
    }

}

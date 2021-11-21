package com.example.hit_se_spt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude ={DataSourceAutoConfiguration.class})
public class HitSeSptApplication {

    public static void main(String[] args) {
        SpringApplication.run(HitSeSptApplication.class, args);
    }

}

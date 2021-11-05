package com.example.penalties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

@SpringBootApplication
@EnableHystrixDashboard
public class PenaltiesApplication {

    public static void main(String[] args) {
        SpringApplication.run(PenaltiesApplication.class, args);
    }

}

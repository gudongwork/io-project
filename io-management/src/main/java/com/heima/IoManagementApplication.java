package com.heima;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.heima", "utils"})
public class IoManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoManagementApplication.class, args);
    }

}

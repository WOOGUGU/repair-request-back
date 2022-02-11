package com.kkkoke.networkrepair;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
@EnableSwagger2
public class NetworkRepairApplication {
    public static void main(String[] args) {
        SpringApplication.run(NetworkRepairApplication.class, args);
    }
}

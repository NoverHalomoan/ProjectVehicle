package com.bengkel.backendBengkel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableAsync
@SpringBootApplication
public class BackendBengkelApplication {

    // @Autowired
    // private EnviromentGetData enviromentGetData;
    public static void main(String[] args) {
        SpringApplication.run(BackendBengkelApplication.class, args);
    }

    @PostConstruct
    public void testEnv() {
        System.out.println("System");
        System.out.println("Email HOST " + System.getenv("MAIL_HOST_DATA"));
        log.info("log info env " + System.getenv("DB_HOST"));
        // log.info("Data Email Host " + enviromentGetData.getEmailHost());
        log.info("Data Email Host env " + System.getenv("MAIL_HOST_DATA"));

    }
}

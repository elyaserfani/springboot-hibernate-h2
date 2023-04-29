package com.simplespringboot.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.simplespringboot.app.repository")
@EntityScan("com.simplespringboot.app.entity")
@SpringBootApplication
public class SimpleSpringBootHibernateApplication implements ApplicationRunner {

    private static final Logger logger = LogManager.getLogger(SimpleSpringBootHibernateApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringBootHibernateApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        logger.debug("Log4j2 logs enabled");
    }
}

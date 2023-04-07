package com.simplespringboot.app;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories("com.simplespringboot.app.Repository")
@EntityScan("com.simplespringboot.app.Model")
@SpringBootApplication

@OpenAPIDefinition(info =@Info(title = "Simple Springboot API", version = "1.0.0", contact = @Contact(name = "Elyas Erfani", email = "elyaserfani2@gmail.com"),  description = "Simple Springboot APIs"))
@SecurityScheme(name = "Bearer Authentication", type = SecuritySchemeType.HTTP, bearerFormat = "JWT", scheme = "bearer")
public class SimpleSpringBootHibernateApplication {

    public static void main(String[] args) {
        SpringApplication.run(SimpleSpringBootHibernateApplication.class, args);
    }

}

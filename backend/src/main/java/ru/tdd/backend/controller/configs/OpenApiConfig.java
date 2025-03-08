package ru.tdd.backend.controller.configs;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@SecurityScheme(
        name = "jwtAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend API")
                        .version("1.0")
                        .description("Backend часть прилоэения")
                ).tags(
                        List.of(
                                new Tag()
                                        .name("Auth Controller")
                                        .description("Контроллер для авторизации и регистрации пользователей"),
                                new Tag()
                                        .name("User Controller")
                                        .description("Контроллер для работы с пользователями"),
                                new Tag()
                                        .name("Post Controller")
                                        .description("Контроллер для работы с должностями"),
                                new Tag()
                                        .name("Organisation Tag Controller")
                                        .description("Контроллер для работы с тэгами организаций")
                        )
                );
    }
}

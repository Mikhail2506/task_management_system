package by.toukachmikhail.taskmanagementsystem.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

  @Bean
  public OpenAPI customOpenAPI() {
    return new OpenAPI()
        .info(new Info()
            .title("Task Management System API")
            .version("1.0")
            .description("API для управления задачами и комментариями"))
        .addSecurityItem(new SecurityRequirement().addList("JWT"))
        .components(new Components()
            .addSecuritySchemes("JWT", new SecurityScheme()
                .name("JWT")
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER) // Указываем, что токен передается в заголовке
                .description("Введите только JWT-токен (без 'Bearer')"))); // Описание для пользователя
  }

}

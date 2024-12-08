package by.toukachmikhail.taskmanagementsystem.configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;

import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "My Task management API", version = "1.0", description = "API Documentation"))
public class SwaggerConfig {

}

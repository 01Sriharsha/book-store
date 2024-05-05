package dev.sriharsha.bookstore.backend.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;


@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "K Sriharsha",
                        email = "harshavasu463@gmail.com",
                        url = "https://sriharsha.vercel.app/"
                ),
                title = "book-store",
                description = "An online book store",
                version = "1.0",
                license = @License(name = "MIT")
        ),
        servers = {
                @Server(
                        description = "Local Environment",
                        url = "http://localhost:8081/api/v1"
                ),
                @Server(
                        description = "Production Environment",
                        url = "http://localhost:8080/api/v1"
                )
        },
        security = {
                @SecurityRequirement(name = "cookieAuth")
        }
)                 
@SecurityScheme(
        name = "cookieAuth",
        description = "App is based upon cookie based jwt authentication",
        type = SecuritySchemeType.HTTP
)
public class OpenApiConfig {
}

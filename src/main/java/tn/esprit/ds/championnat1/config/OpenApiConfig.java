package tn.esprit.ds.championnat1.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Championnat API")
                        .version("1.0.0")
                        .description("API REST de gestion du championnat : sponsors, équipes et pilotes")
                        .contact(new Contact()
                                .name("Équipe DS – ESPRIT")
                                .email("contact@esprit.tn")
                                .url("https://esprit.tn"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}

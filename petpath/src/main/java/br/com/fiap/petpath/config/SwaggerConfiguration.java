package br.com.fiap.petpath.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    final String TIPO_AUTENTICACAO = "bearerAuth";

    @Bean
    OpenAPI configurarSwagger() {
        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(TIPO_AUTENTICACAO))
                .components(new Components()
                        .addSecuritySchemes(TIPO_AUTENTICACAO,
                                new SecurityScheme()
                                        .name(TIPO_AUTENTICACAO)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")))
                .info(new Info()
                        .title("PetPath AI — API de Saude de Pets")
                        .description("API RESTful para gestao continua da saude de animais de estimacao. "
                                + "Desenvolvida como solucao para o FIAP Challenge 2026 — Parceiro CLYVO VET.")
                        .summary("Plataforma de jornada continua de saude do pet")
                        .termsOfService("https://petpathAI.com.br/termos")
                        .version("1.0.0")
                        .license(new License()
                                .url("/planos")
                                .name("PetPath AI — Planos de Uso")));
    }

}

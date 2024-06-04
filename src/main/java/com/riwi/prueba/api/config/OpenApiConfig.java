package com.riwi.prueba.api.config;


import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
@Configuration
@OpenAPIDefinition(info = @Info(title = "API para gestionar encuestas de Riwi", version = "1.0", description = "Documentacion API de adminitracion de entidades del sistema de encuestas"))
public class OpenApiConfig {

}
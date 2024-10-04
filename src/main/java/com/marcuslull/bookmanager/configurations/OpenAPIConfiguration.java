package com.marcuslull.bookmanager.configurations;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {


    @Bean
    public OpenApiCustomizer simplifyResponses() {
        return openApi -> {
            openApi.getPaths().values().forEach(path -> {
                path.readOperations().forEach(operation -> {
                    operation.getResponses().values().forEach(response -> {
                        response.setContent(null);
                    });
                });
            });
        };
    }
}

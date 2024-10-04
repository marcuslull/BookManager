package com.marcuslull.bookmanager.configurations;

import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfiguration {


    /**
     * Customizes the OpenAPI documentation by simplifying the responses.
     *
     * <p>
     * This method configures an OpenApiCustomizer that iterates through all paths
     * and operations in the OpenAPI specification. For each operation, it clears
     * the response content by setting it to null.
     * </p>
     *
     * <p>
     * This process can streamline the documentation and remove any sensitive data
     * from the generated OpenAPI specification.
     * </p>
     *
     * @return An OpenApiCustomizer that simplifies responses by setting their content to null.
     */
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

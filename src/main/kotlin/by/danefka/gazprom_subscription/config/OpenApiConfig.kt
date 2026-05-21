package by.danefka.gazprom_subscription.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import io.swagger.v3.oas.models.tags.Tag
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {

    @Bean
    fun sortTagsCustomizer(): OpenApiCustomizer {
        return OpenApiCustomizer { openApi: OpenAPI ->
            openApi.tags = listOf(
                    Tag()
                            .name("1. Authentication")
                            .description("Authentication and authorization endpoints"),

                    Tag()
                            .name("2. User subscriptions")
                            .description("Operations with current user's subscriptions"),

                    Tag()
                            .name("3. Admin subscriptions")
                            .description("Administrative subscription management")
            )
        }
    }


    @Bean
    fun customOpenAPI(): OpenAPI {

        val securitySchemeName = "bearerAuth"

        return OpenAPI()
                .info(
                        Info()
                                .title("Subscription Service API")
                                .description("REST API for managing user subscriptions")
                                .version("1.0.0")
                )
                .addSecurityItem(
                        SecurityRequirement().addList(securitySchemeName)
                )
                .components(
                        Components()
                                .addSecuritySchemes(
                                        securitySchemeName,
                                        SecurityScheme()
                                                .name(securitySchemeName)
                                                .type(SecurityScheme.Type.HTTP)
                                                .scheme("bearer")
                                                .bearerFormat("JWT")
                                )
                )
    }
}
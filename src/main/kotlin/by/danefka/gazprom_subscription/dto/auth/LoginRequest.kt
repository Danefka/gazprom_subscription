package by.danefka.gazprom_subscription.dto.auth

import io.swagger.v3.oas.annotations.media.Schema

@Schema(description = "Request for user authentication")
data class LoginRequest(

        @field:Schema(description = "Email", example = "danefka@example.com")
        val email: String,

        @field:Schema(description = "Raw password", example = "Admin123")
        val password: String
)
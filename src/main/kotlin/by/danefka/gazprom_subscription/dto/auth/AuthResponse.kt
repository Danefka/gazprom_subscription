package by.danefka.gazprom_subscription.dto.auth

import io.swagger.v3.oas.annotations.media.Schema


@Schema(description = "Authentication response with JWT token")
data class AuthResponse(

        @field:Schema(description = "JWT access token")
        val token: String
)
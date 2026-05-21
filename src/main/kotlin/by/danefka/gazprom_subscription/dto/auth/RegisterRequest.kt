package by.danefka.gazprom_subscription.dto.auth

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size


@Schema(description = "Request for user registration")
data class RegisterRequest(

        @field:Schema(description = "Email", example = "example@example.com")
        @field:Email
        @field:NotBlank
        val email: String,

        @field:Schema(description = "Password", example = "Password123")
        @field:Size(min = 6)
        val password: String
)
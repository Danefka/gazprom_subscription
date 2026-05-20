package by.danefka.gazprom_subscription.dto.auth

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterRequest(

    @field:Email
    @field:NotBlank
    val email: String,

    @field:Size(min = 6)
    val password: String
)
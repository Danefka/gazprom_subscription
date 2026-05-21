package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.dto.auth.AuthResponse
import by.danefka.gazprom_subscription.dto.auth.LoginRequest
import by.danefka.gazprom_subscription.dto.auth.RegisterRequest
import by.danefka.gazprom_subscription.dto.common.ErrorResponse
import by.danefka.gazprom_subscription.service.auth.AuthService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*


@Tag(
        name = "1. Authentication",
        description = "Authentication and authorization endpoints"
)
@RestController
@RequestMapping("/auth")
class AuthController(
        private val authService: AuthService
) {

    @Operation(
            summary = "Register new user",
            description = "Creates a new user account with USER role"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "201",
                        description = "User successfully registered",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = AuthResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "400",
                        description = "Validation error",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "409",
                        description = "User already exists",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    fun register(
            @Valid @RequestBody request: RegisterRequest
    ): AuthResponse {
        return authService.register(request)
    }

    @Operation(
            summary = "Login user",
            description = "Authenticates user and returns JWT access token"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Successfully authenticated",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = AuthResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Invalid credentials",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @PostMapping("/login")
    fun login(
            @Valid @RequestBody request: LoginRequest
    ): AuthResponse {
        return authService.login(request)
    }
}
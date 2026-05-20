package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.dto.auth.*
import by.danefka.gazprom_subscription.service.auth.AuthService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/auth")
class AuthController(
        private val authService: AuthService
) {

    @PostMapping("/register")
    fun register(
            @RequestBody request: RegisterRequest
    ): AuthResponse {

        return authService.register(request)
    }

    @PostMapping("/login")
    fun login(
            @RequestBody request: LoginRequest
    ): AuthResponse {

        return authService.login(request)
    }
}
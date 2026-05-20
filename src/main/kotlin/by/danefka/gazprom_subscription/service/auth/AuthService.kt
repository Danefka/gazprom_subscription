package by.danefka.gazprom_subscription.service.auth

import by.danefka.gazprom_subscription.dto.auth.AuthResponse
import by.danefka.gazprom_subscription.dto.auth.LoginRequest
import by.danefka.gazprom_subscription.dto.auth.RegisterRequest

interface AuthService {

    fun register(
            request: RegisterRequest
    ): AuthResponse

    fun login(
            request: LoginRequest
    ): AuthResponse
}
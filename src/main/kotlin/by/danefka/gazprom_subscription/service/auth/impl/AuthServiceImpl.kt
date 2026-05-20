package by.danefka.gazprom_subscription.service.auth.impl

import by.danefka.gazprom_subscription.dto.auth.AuthResponse
import by.danefka.gazprom_subscription.dto.auth.LoginRequest
import by.danefka.gazprom_subscription.dto.auth.RegisterRequest
import by.danefka.gazprom_subscription.entity.User
import by.danefka.gazprom_subscription.exception.exceptions.InvalidCredentialsException
import by.danefka.gazprom_subscription.exception.exceptions.UserAlreadyExistsException
import by.danefka.gazprom_subscription.repository.UserRepository
import by.danefka.gazprom_subscription.security.JwtService
import by.danefka.gazprom_subscription.service.auth.AuthService
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class AuthServiceImpl(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder,
        private val jwtService: JwtService
) : AuthService {

    override fun register(request: RegisterRequest): AuthResponse {
        if (userRepository.existsByEmail(request.email)) {
            throw UserAlreadyExistsException("User with this email already exists")
        }

        val user = User(
                email = request.email,
                password = passwordEncoder.encode(request.password)
        )

        userRepository.save(user)

        val token = jwtService.generateToken(request.email)

        return AuthResponse(token)
    }

    override fun login(request: LoginRequest): AuthResponse {
        val user = userRepository.findByEmail(request.email)
                .orElseThrow {
                    InvalidCredentialsException("Invalid email or password")
                }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw InvalidCredentialsException("Invalid email or password")
        }

        val token = jwtService.generateToken(request.email)

        return AuthResponse(token)
    }
}
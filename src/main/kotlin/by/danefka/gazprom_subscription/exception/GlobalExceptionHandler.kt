package by.danefka.gazprom_subscription.exception

import by.danefka.gazprom_subscription.dto.common.ErrorResponse
import by.danefka.gazprom_subscription.exception.exceptions.InvalidCredentialsException
import by.danefka.gazprom_subscription.exception.exceptions.UserAlreadyExistsException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(
            ex: RuntimeException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse(
                                status = 400,
                                error = "BAD_REQUEST",
                                message = ex.message ?: "Bad request",
                                path = request.requestURI
                        )
                )
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(
            ex: MethodArgumentNotValidException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        val message = ex.bindingResult
                .fieldErrors
                .joinToString("; ") { "${it.field}: ${it.defaultMessage}" }

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse(
                                status = 400,
                                error = "VALIDATION_ERROR",
                                message = message,
                                path = request.requestURI
                        )
                )
    }

    @ExceptionHandler(InvalidCredentialsException::class)
    fun handleInvalidCredentials(
            ex: InvalidCredentialsException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse(
                                status = 401,
                                error = "UNAUTHORIZED",
                                message = ex.message ?: "Unauthorized",
                                path = request.requestURI
                        )
                )
    }

    @ExceptionHandler(UserAlreadyExistsException::class)
    fun handleUserAlreadyExists(
            ex: UserAlreadyExistsException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(
                        ErrorResponse(
                                status = HttpStatus.CONFLICT.value(),
                                error = HttpStatus.CONFLICT.name,
                                message = ex.message ?: "User already exists",
                                path = request.requestURI
                        )
                )
    }
}
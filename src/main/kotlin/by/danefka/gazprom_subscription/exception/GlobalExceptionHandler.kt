package by.danefka.gazprom_subscription.exception

import by.danefka.gazprom_subscription.dto.common.ErrorResponse
import by.danefka.gazprom_subscription.exception.exceptions.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

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

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFound(
            ex: NotFoundException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse(
                                status = HttpStatus.NOT_FOUND.value(),
                                error = HttpStatus.NOT_FOUND.name,
                                message = ex.message ?: "Resource not found",
                                path = request.requestURI
                        )
                )
    }


    @ExceptionHandler(ForbiddenException::class)
    fun handleForbidden(
            ex: ForbiddenException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(
                        ErrorResponse(
                                status = HttpStatus.FORBIDDEN.value(),
                                error = HttpStatus.FORBIDDEN.name,
                                message = ex.message ?: "Access denied",
                                path = request.requestURI
                        )
                )
    }

    @ExceptionHandler(BusinessRuleException::class)
    fun handleBusinessRule(
            ex: BusinessRuleException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse(
                                status = HttpStatus.BAD_REQUEST.value(),
                                error = HttpStatus.BAD_REQUEST.name,
                                message = ex.message ?: "Business rule violation",
                                path = request.requestURI
                        )
                )
    }


    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(
            ex: RuntimeException,
            request: HttpServletRequest
    ): ResponseEntity<ErrorResponse> {

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse(
                                status = HttpStatus.INTERNAL_SERVER_ERROR.value(),
                                error = HttpStatus.INTERNAL_SERVER_ERROR.name,
                                message = ex.message ?: "Internal server error",
                                path = request.requestURI
                        )
                )
    }
}
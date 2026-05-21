package by.danefka.gazprom_subscription.dto.subscription

import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate


@Schema(description = "Request for creating a subscription")
data class CreateSubscriptionRequest(

        @field:Schema(description = "Service name", example = "Netflix")
        @field:NotBlank(message = "Service name must not be blank")
        val serviceName: String,

        @field:Schema(description = "Tariff name", example = "Premium")
        @field:NotBlank(message = "Tariff name must not be blank")
        val tariffName: String,

        @field:Schema(description = "Subscription price", example = "999.99")
        @field:NotNull(message = "Price must not be null")
        @field:DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
        val price: BigDecimal,

        @field:Schema(description = "Subscription start date", example = "2026-05-01")
        @field:NotNull(message = "Start date must not be null")
        val startDate: LocalDate,

        @field:Schema(description = "Subscription end date", example = "2026-06-01")
        @field:NotNull(message = "End date must not be null")
        @field:Future(message = "End date must be in the future")
        val endDate: LocalDate
)
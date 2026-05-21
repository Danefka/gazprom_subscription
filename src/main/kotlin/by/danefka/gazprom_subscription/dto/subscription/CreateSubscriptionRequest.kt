package by.danefka.gazprom_subscription.dto.subscription

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Future
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal
import java.time.LocalDate

data class CreateSubscriptionRequest(

    @field:NotBlank(message = "Service name must not be blank")
    val serviceName: String,

    @field:NotBlank(message = "Tariff name must not be blank")
    val tariffName: String,

    @field:NotNull(message = "Price must not be null")
    @field:DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    val price: BigDecimal,

    @field:NotNull(message = "Start date must not be null")
    val startDate: LocalDate,

    @field:NotNull(message = "End date must not be null")
    @field:Future(message = "End date must be in the future")
    val endDate: LocalDate
)
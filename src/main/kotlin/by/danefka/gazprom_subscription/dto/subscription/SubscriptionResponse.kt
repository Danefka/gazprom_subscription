package by.danefka.gazprom_subscription.dto.subscription

import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import io.swagger.v3.oas.annotations.media.Schema
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID


@Schema(description = "Subscription response")
data class SubscriptionResponse(
        @field:Schema(description = "Subscription id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val id: UUID,

        @field:Schema(description = "User id", example = "3fa85f64-5717-4562-b3fc-2c963f66afa6")
        val userId: UUID,

        @field:Schema(description = "Service name", example = "Netflix")
        val serviceName: String,

        @field:Schema(description = "Tariff name", example = "Premium")
        val tariffName: String,

        @field:Schema(description = "Subscription price", example = "999.99")
        val price: BigDecimal,

        @field:Schema(description = "Subscription start date", example = "2026-05-01")
        val startDate: LocalDate,

        @field:Schema(description = "Subscription end date", example = "2026-06-01")
        val endDate: LocalDate,

        @field:Schema(description = "Subscription status", example = "ACTIVE")
        val status: SubscriptionStatus,

        val createdAt: LocalDateTime,

        val updatedAt: LocalDateTime?
)
package by.danefka.gazprom_subscription.dto.subscription

import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

data class SubscriptionResponse(

        val id: UUID,

        val userId: UUID,

        val serviceName: String,

        val tariffName: String,

        val price: BigDecimal,

        val startDate: LocalDate,

        val endDate: LocalDate,

        val status: SubscriptionStatus,

        val createdAt: LocalDateTime,

        val updatedAt: LocalDateTime?
)
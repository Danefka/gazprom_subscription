package by.danefka.gazprom_subscription.dto.subscription

import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate
import java.util.UUID

data class AdminSubscriptionFilterRequest(

        val userId: UUID? = null,

        val serviceName: String? = null,

        val status: SubscriptionStatus? = null,

        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val startDateFrom: LocalDate? = null,

        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val startDateTo: LocalDate? = null,

        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val endDateFrom: LocalDate? = null,

        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val endDateTo: LocalDate? = null
)
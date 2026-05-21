package by.danefka.gazprom_subscription.dto.subscription

import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import io.swagger.v3.oas.annotations.media.Schema
import org.springframework.format.annotation.DateTimeFormat
import java.time.LocalDate


@Schema(description = "Filters for current user's subscriptions")
data class SubscriptionFilterRequest(

        @field:Schema(description = "Filter by service name", example = "Netflix")
        val serviceName: String? = null,

        @field:Schema(description = "Filter by subscription status", example = "ACTIVE")
        val status: SubscriptionStatus? = null,

        @field:Schema(description = "Start date from", example = "2026-01-01")
        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val startDateFrom: LocalDate? = null,

        @field:Schema(description = "Start date to", example = "2026-12-31")
        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        val startDateTo: LocalDate? = null,

        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @field:Schema(description = "End date from", example = "2026-01-01")
        val endDateFrom: LocalDate?,

        @field:DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        @field:Schema(description = "End date to", example = "2026-12-31")
        val endDateTo: LocalDate?
)
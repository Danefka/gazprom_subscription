package by.danefka.gazprom_subscription.dto.subscription

import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.NotNull


@Schema(description = "Request for updating subscription status")
data class UpdateSubscriptionStatusRequest(

        @field:Schema(description = "New subscription status", example = "PAUSED")
        @field:NotNull(message = "Status must not be null")
        val status: SubscriptionStatus
)
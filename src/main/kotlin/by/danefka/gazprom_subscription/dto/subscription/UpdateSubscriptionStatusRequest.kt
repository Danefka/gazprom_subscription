package by.danefka.gazprom_subscription.dto.subscription

import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import jakarta.validation.constraints.NotNull

data class UpdateSubscriptionStatusRequest(

    @field:NotNull(message = "Status must not be null")
    val status: SubscriptionStatus
)
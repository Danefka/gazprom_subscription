package by.danefka.gazprom_subscription.mapper

import by.danefka.gazprom_subscription.dto.subscription.SubscriptionResponse
import by.danefka.gazprom_subscription.entity.Subscription
import org.springframework.stereotype.Component

@Component
class SubscriptionMapper {

    fun toResponse(subscription: Subscription): SubscriptionResponse {
        return SubscriptionResponse(
            id = subscription.id!!,
            userId = subscription.user.id!!,
            serviceName = subscription.serviceName,
            tariffName = subscription.tariffName,
            price = subscription.price,
            startDate = subscription.startDate,
            endDate = subscription.endDate,
            status = subscription.status,
            createdAt = subscription.createdAt,
            updatedAt = subscription.updatedAt
        )
    }
}
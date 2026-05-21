package by.danefka.gazprom_subscription.repository

import by.danefka.gazprom_subscription.entity.Subscription
import by.danefka.gazprom_subscription.entity.User
import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SubscriptionRepository :
        JpaRepository<Subscription, UUID>,
        JpaSpecificationExecutor<Subscription> {

    fun findAllByUserAndStatus(
            user: User,
            status: SubscriptionStatus
    ): List<Subscription>
}
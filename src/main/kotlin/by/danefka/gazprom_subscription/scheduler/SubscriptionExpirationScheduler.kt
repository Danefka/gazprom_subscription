package by.danefka.gazprom_subscription.scheduler

import by.danefka.gazprom_subscription.entity.SubscriptionStatusHistory
import by.danefka.gazprom_subscription.enum.StatusChangeReason
import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import by.danefka.gazprom_subscription.repository.SubscriptionRepository
import by.danefka.gazprom_subscription.repository.SubscriptionStatusHistoryRepository
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Component
class SubscriptionExpirationScheduler(
    private val subscriptionRepository: SubscriptionRepository,
    private val historyRepository: SubscriptionStatusHistoryRepository
) {

    @Transactional
    @Scheduled(cron = "0 0 0 * * *")
    fun expireSubscriptions() {
        val today = LocalDate.now()

        val subscriptions = subscriptionRepository.findAllByStatusInAndEndDateBefore(
            listOf(SubscriptionStatus.ACTIVE, SubscriptionStatus.PAUSED),
            today
        )

        subscriptions.forEach { subscription ->
            val oldStatus = subscription.status

            subscription.status = SubscriptionStatus.EXPIRED
            subscription.updatedAt = LocalDateTime.now()

            historyRepository.save(
                SubscriptionStatusHistory(
                    subscription = subscription,
                    oldStatus = oldStatus,
                    newStatus = SubscriptionStatus.EXPIRED,
                    reason = StatusChangeReason.EXPIRED_BY_SCHEDULER
                )
            )
        }

        subscriptionRepository.saveAll(subscriptions)
    }
}
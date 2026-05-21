package by.danefka.gazprom_subscription.repository

import by.danefka.gazprom_subscription.entity.SubscriptionStatusHistory
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface SubscriptionStatusHistoryRepository : JpaRepository<SubscriptionStatusHistory, UUID>
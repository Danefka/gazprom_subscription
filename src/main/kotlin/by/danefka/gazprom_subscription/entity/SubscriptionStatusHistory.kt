package by.danefka.gazprom_subscription.entity

import by.danefka.gazprom_subscription.enum.StatusChangeReason
import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "subscription_status_history")
class SubscriptionStatusHistory(

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subscription_id", nullable = false)
    var subscription: Subscription,

    @Enumerated(EnumType.STRING)
    @Column(name = "old_status")
    var oldStatus: SubscriptionStatus? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "new_status", nullable = false)
    var newStatus: SubscriptionStatus,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    var reason: StatusChangeReason,

    @Column(name = "changed_at", nullable = false)
    var changedAt: LocalDateTime = LocalDateTime.now()
)
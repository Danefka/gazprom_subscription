package by.danefka.gazprom_subscription.entity

import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import jakarta.persistence.*
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "subscriptions")
class Subscription(

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "user_id", nullable = false)
        var user: User,

        @Column(nullable = false)
        var serviceName: String,


        @Column(nullable = false)
        var tariffName: String,

        @Column(nullable = false)
        var price: BigDecimal,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var status: SubscriptionStatus = SubscriptionStatus.ACTIVE,

        @Column(nullable = false)
        var startDate: LocalDate,

        @Column(nullable = false)
        var endDate: LocalDate,

        @Column(nullable = false)
        var createdAt: LocalDateTime = LocalDateTime.now(),

        var updatedAt: LocalDateTime? = null
)
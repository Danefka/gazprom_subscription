package by.danefka.gazprom_subscription.entity

import by.danefka.gazprom_subscription.enum.UserRole
import jakarta.persistence.*
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "users")
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.UUID)
        var id: UUID? = null,

        @Column(nullable = false, unique = true)
        var email: String,

        @Column(nullable = false)
        var password: String,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var role: UserRole = UserRole.USER,

        @Column(nullable = false)
        var createdAt: LocalDateTime = LocalDateTime.now()
)
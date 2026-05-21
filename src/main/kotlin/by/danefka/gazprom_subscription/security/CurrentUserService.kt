package by.danefka.gazprom_subscription.security

import by.danefka.gazprom_subscription.entity.User
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class CurrentUserService {

    fun getCurrentUser(): User {
        val authentication = SecurityContextHolder.getContext().authentication

        val principal = authentication.principal as CustomUserDetails

        return principal.user
    }
}
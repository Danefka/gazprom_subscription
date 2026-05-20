package by.danefka.gazprom_subscription.security

import by.danefka.gazprom_subscription.entity.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class CustomUserDetails(private val user: User) : UserDetails{
    override fun getAuthorities(): Collection<GrantedAuthority> {
        return listOf(
                SimpleGrantedAuthority("ROLE_${user.role.name}")
        )
    }

    override fun getPassword(): String {
        return user.password
    }

    override fun getUsername(): String {
        return user.email
    }
}
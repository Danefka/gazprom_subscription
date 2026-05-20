package by.danefka.gazprom_subscription.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.util.*
import javax.crypto.SecretKey

@Service
class JwtService(

        @Value("\${jwt.secret}")
        private val secret: String,

        @Value("\${jwt.expiration}")
        private val expiration: Long
) {

    private fun getSigningKey(): SecretKey {

        return Keys.hmacShaKeyFor(secret.toByteArray())
    }

    fun generateToken(email: String): String {

        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(Date())
                .setExpiration(
                        Date(System.currentTimeMillis() + expiration)
                )
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact()
    }

    fun extractEmail(token: String): String {

        return extractAllClaims(token).subject
    }

    fun isTokenValid(token: String): Boolean {

        return try {

            extractAllClaims(token)

            true

        } catch (e: Exception) {

            false
        }
    }

    private fun extractAllClaims(token: String): Claims {

        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .body
    }
}
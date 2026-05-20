package by.danefka.gazprom_subscription.config

import by.danefka.gazprom_subscription.security.CustomAccessDeniedHandler
import by.danefka.gazprom_subscription.security.CustomAuthenticationEntryPoint
import by.danefka.gazprom_subscription.security.JwtAuthFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
class SecurityConfig(

        private val jwtAuthFilter: JwtAuthFilter,

        private val authenticationEntryPoint: CustomAuthenticationEntryPoint,

        private val accessDeniedHandler: CustomAccessDeniedHandler
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {

        http
                .csrf { it.disable() }

                .sessionManagement {
                    it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                }

                .exceptionHandling {

                    it.authenticationEntryPoint(authenticationEntryPoint)

                    it.accessDeniedHandler(accessDeniedHandler)
                }

                .authorizeHttpRequests {

                    it.requestMatchers(
                            "/auth/**",
                            "/swagger-ui/**",
                            "/swagger-ui.html",
                            "/v3/api-docs/**"
                    ).permitAll()

                    it.anyRequest().authenticated()
                }

                .addFilterBefore(
                        jwtAuthFilter,
                        UsernamePasswordAuthenticationFilter::class.java
                )

        return http.build()
    }
}
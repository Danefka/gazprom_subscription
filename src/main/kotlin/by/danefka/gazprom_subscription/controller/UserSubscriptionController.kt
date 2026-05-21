package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.dto.subscription.CreateSubscriptionRequest
import by.danefka.gazprom_subscription.dto.subscription.SubscriptionFilterRequest
import by.danefka.gazprom_subscription.dto.subscription.SubscriptionResponse
import by.danefka.gazprom_subscription.service.subscription.SubscriptionService
import jakarta.validation.Valid
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.ResponseEntity

@RestController
@RequestMapping("/subscriptions")
@PreAuthorize("hasRole('USER')")
class UserSubscriptionController(
        private val subscriptionService: SubscriptionService
) {

    @PostMapping
    fun create(
            @Valid @RequestBody request: CreateSubscriptionRequest
    ): SubscriptionResponse {
        return subscriptionService.create(request)
    }

    @GetMapping("/{id}")
    fun getById(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.getById(id)
    }

    @GetMapping("/my")
    fun getMySubscriptions(
            filter: SubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse> {
        return subscriptionService.getMySubscriptions(filter, pageable)
    }

    @GetMapping("/my/active")
    fun getMyActiveSubscriptions(): List<SubscriptionResponse> {
        return subscriptionService.getMyActiveSubscriptions()
    }

    @PostMapping("/{id}/pause")
    fun pause(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.pause(id)
    }

    @PostMapping("/{id}/cancel")
    fun cancel(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.cancel(id)
    }

    @PatchMapping("/{id}/resume")
    fun resume(@PathVariable id: UUID): ResponseEntity<SubscriptionResponse> {
        return ResponseEntity.ok(subscriptionService.resume(id))
    }
}
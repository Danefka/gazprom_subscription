package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.dto.subscription.AdminSubscriptionFilterRequest
import by.danefka.gazprom_subscription.dto.subscription.SubscriptionResponse
import by.danefka.gazprom_subscription.dto.subscription.UpdateSubscriptionStatusRequest
import by.danefka.gazprom_subscription.service.subscription.SubscriptionService
import org.springframework.security.access.prepost.PreAuthorize
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.*
import java.util.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@RestController
@RequestMapping("/admin/subscriptions")
@PreAuthorize("hasRole('ADMIN')")
class AdminSubscriptionController(
        private val subscriptionService: SubscriptionService
) {

    @GetMapping
    fun getAllSubscriptions(
            filter: AdminSubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse> {
        return subscriptionService.getAllSubscriptions(filter, pageable)
    }

    @GetMapping("/{id}")
    fun getById(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.getSubscriptionByIdAsAdmin(id)
    }

    @PatchMapping("/{id}/status")
    fun changeStatus(
            @PathVariable id: UUID,
            @Valid @RequestBody request: UpdateSubscriptionStatusRequest
    ): SubscriptionResponse {
        return subscriptionService.changeStatusAsAdmin(id, request)
    }
}
package by.danefka.gazprom_subscription.service.subscription

import by.danefka.gazprom_subscription.dto.subscription.*
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import java.util.*
interface SubscriptionService {

    fun create(request: CreateSubscriptionRequest): SubscriptionResponse

    fun getById(id: UUID): SubscriptionResponse

    fun getMySubscriptions(
            filter: SubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse>

    fun getMyActiveSubscriptions(): List<SubscriptionResponse>

    fun pause(id: UUID): SubscriptionResponse

    fun cancel(id: UUID): SubscriptionResponse


    fun getAllSubscriptions(
            filter: AdminSubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse>

    fun getSubscriptionByIdAsAdmin(id: UUID): SubscriptionResponse

    fun changeStatusAsAdmin(
            id: UUID,
            request: UpdateSubscriptionStatusRequest
    ): SubscriptionResponse

    fun resume(id: UUID): SubscriptionResponse
}
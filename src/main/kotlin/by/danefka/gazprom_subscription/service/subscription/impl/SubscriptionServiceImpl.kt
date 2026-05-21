package by.danefka.gazprom_subscription.service.subscription.impl

import by.danefka.gazprom_subscription.dto.subscription.*
import by.danefka.gazprom_subscription.entity.Subscription
import by.danefka.gazprom_subscription.enum.SubscriptionStatus
import by.danefka.gazprom_subscription.exception.exceptions.BusinessRuleException
import by.danefka.gazprom_subscription.exception.exceptions.ForbiddenException
import by.danefka.gazprom_subscription.exception.exceptions.NotFoundException
import by.danefka.gazprom_subscription.mapper.SubscriptionMapper
import by.danefka.gazprom_subscription.repository.SubscriptionRepository
import by.danefka.gazprom_subscription.security.CurrentUserService
import by.danefka.gazprom_subscription.service.subscription.SubscriptionService
import by.danefka.gazprom_subscription.specification.SubscriptionSpecification
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.util.UUID

@Service
class SubscriptionServiceImpl(
        private val subscriptionRepository: SubscriptionRepository,
        private val currentUserService: CurrentUserService,
        private val subscriptionMapper: SubscriptionMapper
) : SubscriptionService {

    @Transactional
    override fun create(request: CreateSubscriptionRequest): SubscriptionResponse {
        validateDates(request.startDate, request.endDate)

        val currentUser = currentUserService.getCurrentUser()

        val subscription = Subscription(
                user = currentUser,
                serviceName = request.serviceName,
                tariffName = request.tariffName,
                price = request.price,
                startDate = request.startDate,
                endDate = request.endDate,
                status = SubscriptionStatus.ACTIVE
        )

        return subscriptionMapper.toResponse(
                subscriptionRepository.save(subscription)
        )
    }

    @Transactional(readOnly = true)
    override fun getById(id: UUID): SubscriptionResponse {
        val subscription = findSubscriptionById(id)

        checkOwner(subscription)

        return subscriptionMapper.toResponse(subscription)
    }

    @Transactional(readOnly = true)
    override fun getMySubscriptions(
            filter: SubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse> {

        val currentUser = currentUserService.getCurrentUser()

        val specification = SubscriptionSpecification.forUser(
                currentUser,
                filter
        )

        return subscriptionRepository
                .findAll(specification, pageable)
                .map { subscriptionMapper.toResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun getMyActiveSubscriptions(): List<SubscriptionResponse> {
        val currentUser = currentUserService.getCurrentUser()

        return subscriptionRepository
                .findAllByUserAndStatus(currentUser, SubscriptionStatus.ACTIVE)
                .map { subscriptionMapper.toResponse(it) }
    }

    @Transactional
    override fun pause(id: UUID): SubscriptionResponse {
        val subscription = findSubscriptionById(id)

        checkOwner(subscription)

        if (subscription.status != SubscriptionStatus.ACTIVE) {
            throw BusinessRuleException("Only active subscription can be paused")
        }

        subscription.status = SubscriptionStatus.PAUSED

        return subscriptionMapper.toResponse(
                subscriptionRepository.save(subscription)
        )
    }

    @Transactional
    override fun cancel(id: UUID): SubscriptionResponse {
        val subscription = findSubscriptionById(id)

        checkOwner(subscription)

        if (subscription.status != SubscriptionStatus.ACTIVE &&
                subscription.status != SubscriptionStatus.PAUSED
        ) {
            throw BusinessRuleException("Only active or paused subscription can be canceled")
        }

        subscription.status = SubscriptionStatus.CANCELED

        return subscriptionMapper.toResponse(
                subscriptionRepository.save(subscription)
        )
    }

    @Transactional(readOnly = true)
    override fun getAllSubscriptions(
            filter: AdminSubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse> {

        val specification = SubscriptionSpecification.forAdmin(filter)

        return subscriptionRepository
                .findAll(specification, pageable)
                .map { subscriptionMapper.toResponse(it) }
    }

    @Transactional(readOnly = true)
    override fun getSubscriptionByIdAsAdmin(id: UUID): SubscriptionResponse {
        val subscription = findSubscriptionById(id)

        return subscriptionMapper.toResponse(subscription)
    }

    @Transactional
    override fun changeStatusAsAdmin(
            id: UUID,
            request: UpdateSubscriptionStatusRequest
    ): SubscriptionResponse {
        val subscription = findSubscriptionById(id)

        validateStatusTransition(subscription.status, request.status)

        subscription.status = request.status

        return subscriptionMapper.toResponse(
                subscriptionRepository.save(subscription)
        )
    }

    private fun findSubscriptionById(id: UUID): Subscription {
        return subscriptionRepository.findById(id)
                .orElseThrow {
                    NotFoundException("Subscription not found")
                }
    }

    private fun checkOwner(subscription: Subscription) {
        val currentUser = currentUserService.getCurrentUser()

        if (subscription.user.id != currentUser.id) {
            throw ForbiddenException("Access denied")
        }
    }

    private fun validateDates(startDate: LocalDate, endDate: LocalDate) {
        if (!endDate.isAfter(startDate)) {
            throw BusinessRuleException("End date must be after start date")
        }
    }

    private fun validateStatusTransition(
            currentStatus: SubscriptionStatus,
            newStatus: SubscriptionStatus
    ) {
        if (currentStatus == newStatus) {
            return
        }

        if (currentStatus == SubscriptionStatus.EXPIRED &&
                newStatus == SubscriptionStatus.ACTIVE
        ) {
            throw BusinessRuleException("Expired subscription cannot be activated without renewal")
        }

        if (currentStatus == SubscriptionStatus.CANCELED) {
            throw BusinessRuleException("Canceled subscription status cannot be changed")
        }

        if (currentStatus == SubscriptionStatus.EXPIRED &&
                newStatus == SubscriptionStatus.PAUSED
        ) {
            throw BusinessRuleException("Expired subscription cannot be paused")
        }
    }
}
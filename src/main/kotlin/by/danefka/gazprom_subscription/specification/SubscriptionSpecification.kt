package by.danefka.gazprom_subscription.specification

import by.danefka.gazprom_subscription.dto.subscription.AdminSubscriptionFilterRequest
import by.danefka.gazprom_subscription.dto.subscription.SubscriptionFilterRequest
import by.danefka.gazprom_subscription.entity.Subscription
import by.danefka.gazprom_subscription.entity.User
import org.springframework.data.jpa.domain.Specification

object SubscriptionSpecification {

    fun forUser(
            user: User,
            filter: SubscriptionFilterRequest
    ): Specification<Subscription> {

        return userEquals(user)
                .and(serviceNameContains(filter.serviceName))
                .and(statusEquals(filter.status))
                .and(startDateFrom(filter.startDateFrom))
                .and(startDateTo(filter.startDateTo))
    }


    fun forAdmin(
            filter: AdminSubscriptionFilterRequest
    ): Specification<Subscription> {

        return userIdEquals(filter.userId)
                .and(serviceNameContains(filter.serviceName))
                .and(statusEquals(filter.status))
                .and(startDateFrom(filter.startDateFrom))
                .and(startDateTo(filter.startDateTo))
                .and(endDateFrom(filter.endDateFrom))
                .and(endDateTo(filter.endDateTo))
    }

    private fun userEquals(user: User): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            criteriaBuilder.equal(root.get<User>("user"), user)
        }
    }

    private fun userIdEquals(userId: java.util.UUID?): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            if (userId == null) {
                criteriaBuilder.conjunction()
            } else {
                criteriaBuilder.equal(root.get<User>("user").get<java.util.UUID>("id"), userId)
            }
        }
    }

    private fun serviceNameContains(serviceName: String?): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            if (serviceName.isNullOrBlank()) {
                criteriaBuilder.conjunction()
            } else {
                criteriaBuilder.like(
                        criteriaBuilder.lower(root.get("serviceName")),
                        "%${serviceName.lowercase()}%"
                )
            }
        }
    }

    private fun statusEquals(status: by.danefka.gazprom_subscription.enum.SubscriptionStatus?): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            if (status == null) {
                criteriaBuilder.conjunction()
            } else {
                criteriaBuilder.equal(root.get<by.danefka.gazprom_subscription.enum.SubscriptionStatus>("status"), status)
            }
        }
    }

    private fun startDateFrom(startDateFrom: java.time.LocalDate?): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            if (startDateFrom == null) {
                criteriaBuilder.conjunction()
            } else {
                criteriaBuilder.greaterThanOrEqualTo(root.get("startDate"), startDateFrom)
            }
        }
    }

    private fun startDateTo(startDateTo: java.time.LocalDate?): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            if (startDateTo == null) {
                criteriaBuilder.conjunction()
            } else {
                criteriaBuilder.lessThanOrEqualTo(root.get("startDate"), startDateTo)
            }
        }
    }

    private fun endDateFrom(endDateFrom: java.time.LocalDate?): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            if (endDateFrom == null) {
                criteriaBuilder.conjunction()
            } else {
                criteriaBuilder.greaterThanOrEqualTo(root.get("endDate"), endDateFrom)
            }
        }
    }

    private fun endDateTo(endDateTo: java.time.LocalDate?): Specification<Subscription> {
        return Specification { root, _, criteriaBuilder ->
            if (endDateTo == null) {
                criteriaBuilder.conjunction()
            } else {
                criteriaBuilder.lessThanOrEqualTo(root.get("endDate"), endDateTo)
            }
        }
    }
}
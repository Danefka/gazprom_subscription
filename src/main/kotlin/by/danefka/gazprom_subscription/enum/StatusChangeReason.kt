package by.danefka.gazprom_subscription.enum

enum class StatusChangeReason {
    CREATED,
    PAUSED,
    RESUMED,
    CANCELLED,
    EXPIRED_BY_SCHEDULER,
    UPDATED_BY_ADMIN
}
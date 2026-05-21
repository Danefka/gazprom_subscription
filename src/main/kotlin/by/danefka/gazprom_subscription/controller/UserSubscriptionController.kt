package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.dto.common.ErrorResponse
import by.danefka.gazprom_subscription.dto.subscription.CreateSubscriptionRequest
import by.danefka.gazprom_subscription.dto.subscription.SubscriptionFilterRequest
import by.danefka.gazprom_subscription.dto.subscription.SubscriptionResponse
import by.danefka.gazprom_subscription.service.subscription.SubscriptionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID


@Tag(
        name = "2. User subscriptions",
        description = "Operations with current user's subscriptions"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/subscriptions")
@PreAuthorize("hasRole('USER')")
class UserSubscriptionController(
        private val subscriptionService: SubscriptionService
) {

    @Operation(
            summary = "Create subscription",
            description = "Creates a new active subscription for current user"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "201",
                        description = "Subscription successfully created",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = SubscriptionResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "400",
                        description = "Validation error",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "403",
                        description = "User access required",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun create(
            @Valid @RequestBody request: CreateSubscriptionRequest
    ): SubscriptionResponse {
        return subscriptionService.create(request)
    }

    @Operation(
            summary = "Get subscription by id",
            description = "Returns subscription details if it belongs to current user"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Subscription found",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = SubscriptionResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "403",
                        description = "Access denied",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "404",
                        description = "Subscription not found",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @GetMapping("/{id}")
    fun getById(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.getById(id)
    }

    @Operation(
            summary = "Get current user's subscriptions",
            description = "Returns paginated list of subscriptions with filtering and sorting"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Subscriptions successfully retrieved"
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "403",
                        description = "User access required",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @GetMapping("/my")
    fun getMySubscriptions(
            filter: SubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse> {
        return subscriptionService.getMySubscriptions(filter, pageable)
    }

    @Operation(
            summary = "Get active subscriptions",
            description = "Returns all active subscriptions of current user"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Active subscriptions successfully retrieved",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    array = ArraySchema(
                                            schema = Schema(implementation = SubscriptionResponse::class)
                                    )
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "403",
                        description = "User access required",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @GetMapping("/my/active")
    fun getMyActiveSubscriptions(): List<SubscriptionResponse> {
        return subscriptionService.getMyActiveSubscriptions()
    }

    @Operation(
            summary = "Pause subscription",
            description = "Changes subscription status from ACTIVE to PAUSED"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Subscription successfully paused",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = SubscriptionResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "400",
                        description = "Invalid subscription status",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "403",
                        description = "Access denied",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "404",
                        description = "Subscription not found",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @PatchMapping("/{id}/pause")
    fun pause(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.pause(id)
    }

    @Operation(
            summary = "Cancel subscription",
            description = "Cancels active or paused subscription"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Subscription successfully canceled",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = SubscriptionResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "400",
                        description = "Invalid subscription status",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "403",
                        description = "Access denied",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "404",
                        description = "Subscription not found",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @PatchMapping("/{id}/cancel")
    fun cancel(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.cancel(id)
    }

    @Operation(
            summary = "Resume subscription",
            description = "Changes subscription status from PAUSED to ACTIVE"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Subscription successfully resumed",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = SubscriptionResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "400",
                        description = "Expired subscription cannot be resumed",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "401",
                        description = "Unauthorized",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "403",
                        description = "Access denied",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "404",
                        description = "Subscription not found",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @PatchMapping("/{id}/resume")
    fun resume(
            @PathVariable id: UUID
    ): SubscriptionResponse {
        return subscriptionService.resume(id)
    }
}
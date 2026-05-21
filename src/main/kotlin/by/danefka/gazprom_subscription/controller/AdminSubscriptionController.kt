package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.dto.common.ErrorResponse
import by.danefka.gazprom_subscription.dto.subscription.AdminSubscriptionFilterRequest
import by.danefka.gazprom_subscription.dto.subscription.SubscriptionResponse
import by.danefka.gazprom_subscription.dto.subscription.UpdateSubscriptionStatusRequest
import by.danefka.gazprom_subscription.service.subscription.SubscriptionService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import java.util.UUID


@Tag(
        name = "3. Admin subscriptions",
        description = "Administrative subscription management"
)
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/admin/subscriptions")
@PreAuthorize("hasRole('ADMIN')")
class AdminSubscriptionController(
        private val subscriptionService: SubscriptionService
) {

    @Operation(
            summary = "Get all subscriptions",
            description = "Returns paginated list of all subscriptions with filters"
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
                        description = "Admin access required",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = ErrorResponse::class)
                            )
                        ]
                )
            ]
    )
    @GetMapping
    fun getAllSubscriptions(
            filter: AdminSubscriptionFilterRequest,
            pageable: Pageable
    ): Page<SubscriptionResponse> {
        return subscriptionService.getAllSubscriptions(filter, pageable)
    }

    @Operation(
            summary = "Get subscription by id as admin",
            description = "Returns subscription details for administrators"
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
                        description = "Admin access required",
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
        return subscriptionService.getSubscriptionByIdAsAdmin(id)
    }

    @Operation(
            summary = "Change subscription status",
            description = "Allows administrator to update subscription status"
    )
    @ApiResponses(
            value = [
                ApiResponse(
                        responseCode = "200",
                        description = "Subscription status successfully updated",
                        content = [
                            Content(
                                    mediaType = "application/json",
                                    schema = Schema(implementation = SubscriptionResponse::class)
                            )
                        ]
                ),
                ApiResponse(
                        responseCode = "400",
                        description = "Invalid status transition",
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
                        description = "Admin access required",
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
    @PatchMapping("/{id}/status")
    fun changeStatus(
            @PathVariable id: UUID,
            @Valid @RequestBody request: UpdateSubscriptionStatusRequest
    ): SubscriptionResponse {
        return subscriptionService.changeStatusAsAdmin(id, request)
    }
}
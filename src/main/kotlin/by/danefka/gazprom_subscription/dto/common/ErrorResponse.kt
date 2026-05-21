package by.danefka.gazprom_subscription.dto.common

import io.swagger.v3.oas.annotations.media.Schema
import java.time.LocalDateTime


@Schema(description = "API error response")
data class ErrorResponse(

        val timestamp: LocalDateTime = LocalDateTime.now(),

        val status: Int,

        val error: String,

        val message: String,

        val path: String
)
package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.BaseIntegrationTest
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.get
import org.springframework.test.web.servlet.patch
import org.springframework.test.web.servlet.post
import java.util.UUID

class UserSubscriptionControllerIntegrationTest : BaseIntegrationTest() {

    @Test
    fun `should create subscription`() {
        val token = registerAndGetToken("sub-create@test.com")

        mockMvc.post("/subscriptions") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            contentType = MediaType.APPLICATION_JSON
            content = createSubscriptionJson()
        }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.id", notNullValue())
                    jsonPath("$.serviceName") { value("Netflix") }
                    jsonPath("$.tariffName") { value("Premium") }
                    jsonPath("$.status") { value("ACTIVE") }
                }
    }

    @Test
    fun `should get subscription by id`() {
        val token = registerAndGetToken("sub-get@test.com")
        val subscriptionId = createSubscriptionAndGetId(token)

        mockMvc.get("/subscriptions/$subscriptionId") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.id") { value(subscriptionId.toString()) }
                    jsonPath("$.serviceName") { value("Netflix") }
                }
    }

    @Test
    fun `should get my active subscriptions`() {
        val token = registerAndGetToken("sub-active@test.com")
        createSubscriptionAndGetId(token)

        mockMvc.get("/subscriptions/my/active") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$[0].serviceName") { value("Netflix") }
                    jsonPath("$[0].status") { value("ACTIVE") }
                }
    }

    @Test
    fun `should pause subscription`() {
        val token = registerAndGetToken("sub-pause@test.com")
        val subscriptionId = createSubscriptionAndGetId(token)

        mockMvc.patch("/subscriptions/$subscriptionId/pause") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.status") { value("PAUSED") }
                }
    }

    @Test
    fun `should resume subscription`() {
        val token = registerAndGetToken("sub-resume@test.com")
        val subscriptionId = createSubscriptionAndGetId(token)

        mockMvc.patch("/subscriptions/$subscriptionId/pause") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.status") { value("PAUSED") }
                }

        mockMvc.patch("/subscriptions/$subscriptionId/resume") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.status") { value("ACTIVE") }
                }
    }

    @Test
    fun `should cancel subscription`() {
        val token = registerAndGetToken("sub-cancel@test.com")
        val subscriptionId = createSubscriptionAndGetId(token)

        mockMvc.patch("/subscriptions/$subscriptionId/cancel") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.status") { value("CANCELED") }
                }
    }

    @Test
    fun `should not get another user subscription`() {
        val ownerToken = registerAndGetToken("owner@test.com")
        val anotherUserToken = registerAndGetToken("another@test.com")

        val subscriptionId = createSubscriptionAndGetId(ownerToken)

        mockMvc.get("/subscriptions/$subscriptionId") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $anotherUserToken")
        }
                .andExpect {
                    status { isForbidden() }
                    jsonPath("$.message") { value("Access denied") }
                }
    }

    @Test
    fun `should not create subscription without token`() {
        mockMvc.post("/subscriptions") {
            contentType = MediaType.APPLICATION_JSON
            content = createSubscriptionJson()
        }
                .andExpect {
                    status { isUnauthorized() }
                    jsonPath("$.message") { value("Authentication required") }
                }
    }

    private fun createSubscriptionAndGetId(token: String): UUID {
        val response = mockMvc.post("/subscriptions") {
            header(HttpHeaders.AUTHORIZATION, "Bearer $token")
            contentType = MediaType.APPLICATION_JSON
            content = createSubscriptionJson()
        }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.id", notNullValue())
                }
                .andReturn()
                .response
                .contentAsString

        val id = response
                .substringAfter("\"id\":\"")
                .substringBefore("\"")

        return UUID.fromString(id)
    }

    private fun createSubscriptionJson(): String {
        return """
            {
              "serviceName": "Netflix",
              "tariffName": "Premium",
              "price": 999.99,
              "startDate": "2026-05-21",
              "endDate": "2026-06-21"
            }
        """.trimIndent()
    }
}
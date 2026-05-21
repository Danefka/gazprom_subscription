package by.danefka.gazprom_subscription

import org.hamcrest.Matchers.notNullValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post
import org.testcontainers.containers.PostgreSQLContainer

@SpringBootTest
@AutoConfigureMockMvc
abstract class BaseIntegrationTest {

    companion object {

        @JvmStatic
        @ServiceConnection
        val postgres: PostgreSQLContainer<*> = PostgreSQLContainer("postgres:16").apply {
            start()
        }
    }

    @Autowired
    protected lateinit var mockMvc: MockMvc

    protected fun registerAndGetToken(email: String): String {
        val response = mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "$email",
                  "password": "password123"
                }
            """.trimIndent()
        }
                .andExpect {
                    status { isCreated() }
                    jsonPath("$.token", notNullValue())
                }
                .andReturn()
                .response
                .contentAsString

        return response
                .substringAfter("\"token\":\"")
                .substringBefore("\"")
    }
}
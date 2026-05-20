package by.danefka.gazprom_subscription.controller

import by.danefka.gazprom_subscription.BaseIntegrationTest
import org.hamcrest.Matchers.notNullValue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerIntegrationTest : BaseIntegrationTest() {

    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `should register user`() {
        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "test1@mail.com",
                  "password": "password123"
                }
            """.trimIndent()
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.token", notNullValue())
                }
    }

    @Test
    fun `should not register user with existing email`() {
        val body = """
            {
              "email": "test2@mail.com",
              "password": "password123"
            }
        """.trimIndent()

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }
                .andExpect {
                    status { isOk() }
                }

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }
                .andExpect {
                    status { isConflict() }
                    jsonPath("$.message") { value("User with this email already exists") }
                }.andDo { print() }
    }

    @Test
    fun `should login user`() {
        val body = """
            {
              "email": "test3@mail.com",
              "password": "password123"
            }
        """.trimIndent()

        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = body
        }
                .andExpect {
                    status { isOk() }
                    jsonPath("$.token", notNullValue())
                }
    }

    @Test
    fun `should not login with invalid password`() {
        mockMvc.post("/auth/register") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "test4@mail.com",
                  "password": "password123"
                }
            """.trimIndent()
        }

        mockMvc.post("/auth/login") {
            contentType = MediaType.APPLICATION_JSON
            content = """
                {
                  "email": "test4@mail.com",
                  "password": "wrong-password"
                }
            """.trimIndent()
        }
                .andExpect {
                    status { isUnauthorized() }
                    jsonPath("$.message") { value("Invalid email or password") }
                }
    }
}
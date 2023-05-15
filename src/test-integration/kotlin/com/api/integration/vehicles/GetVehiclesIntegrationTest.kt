package com.api.integration.vehicles

import com.api.shared.Application
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import kotlin.test.assertTrue

@SpringBootTest(classes = [Application::class])
@AutoConfigureMockMvc
class GetVehiclesIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetTheVehicles() {
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect { MockMvcResultMatchers.jsonPath("$.data").exists() }
            .andExpect { MockMvcResultMatchers.jsonPath("$.data").isArray() }
    }

    @Test
    fun shouldThrowNotFoundException() {
        mockMvc.perform(MockMvcRequestBuilders.get("/vehicles/unknown"))
            .andExpect(MockMvcResultMatchers.status().isNotFound)
            .andExpect {
                assertTrue { it.response.contentAsString.contains(Regex("\"status\":404")) }
                assertTrue { it.response.contentAsString.contains(Regex("\"message\":\"Vehicle id unknown not found\"")) }
                assertTrue { it.response.contentAsString.contains(Regex("\"context\":\"NotFoundException\"")) }
            }
    }

}
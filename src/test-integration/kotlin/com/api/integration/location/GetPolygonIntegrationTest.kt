package com.api.integration.location

import com.api.shared.Application
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers


@SpringBootTest(classes = [Application::class])
@AutoConfigureMockMvc
class GetPolygonIntegrationTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun shouldGetThePolygons() {
        val expectedNumberOfPolygonsInJson = 154
        mockMvc.perform(MockMvcRequestBuilders.get("/polygons"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.jsonPath("$.data").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data.length()").value(expectedNumberOfPolygonsInJson))
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].id").isString())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].name").isString())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].legacyId").isString())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].geometry").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].geometry.coordinates").isArray())
            .andExpect(MockMvcResultMatchers.jsonPath("$.data[0].geometry.type").isString())
    }
}
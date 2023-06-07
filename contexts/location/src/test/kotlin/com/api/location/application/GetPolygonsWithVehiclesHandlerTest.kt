package com.api.location.application

import com.api.location.domain.PolygonRepository
import com.api.shared.application.dto.PolygonWithVehicles
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals


class GetPolygonsWithVehiclesHandlerTest {

    private lateinit var repository: PolygonRepository
    private lateinit var handler: GetPolygonByIdWithVehiclesHandler

    @BeforeEach
    fun init() {
        repository = mockk<PolygonRepository>()
        handler = GetPolygonByIdWithVehiclesHandler(repository)
    }

    @Test
    fun shouldReturnInstanceOfPolygonWithVehicles() {
        val expectedResponse = PolygonWithVehicles(id = "MOCK", listOf("1", "2", "3"))
        val stringCapturingSlot = slot<String>()
        every { repository.getPolygonByIDWithVehicles(any()) } returns expectedResponse
        val result = handler.execute(expectedResponse.id)
        assertEquals(result.id, expectedResponse.id)
        assertEquals(result.vehicles, expectedResponse.vehicles)
        verify(exactly = 1) { repository.getPolygonByIDWithVehicles(capture(stringCapturingSlot)) }
        val capturedParameterId = stringCapturingSlot.captured
        assertEquals(capturedParameterId, expectedResponse.id)

    }
}
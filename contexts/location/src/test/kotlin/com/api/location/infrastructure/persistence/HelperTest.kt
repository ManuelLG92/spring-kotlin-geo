package com.api.location.infrastructure.persistence

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class HelperTest {

    private lateinit var helpers: Helpers

    @BeforeEach
    fun init() {
        helpers = Helpers()
    }

    @Test
    fun shouldGetPolygonsWithVehiclesList() {
        val keyMapPrefix = "polygon"
        val valuesMapPrefix = "vehicles"
        val vehiclesInPolygonOne = mutableListOf<String>(
            "${valuesMapPrefix}_2",
            "${valuesMapPrefix}_3",
            "${valuesMapPrefix}_5"
        )
        val vehiclesInPolygonTwo = mutableListOf<String>(
            "${valuesMapPrefix}_2",
            "${valuesMapPrefix}_3",
            "${valuesMapPrefix}_5"
        )
        val polygonWithVehicles = mutableMapOf<String, MutableList<String>>(
            "${keyMapPrefix}_1" to vehiclesInPolygonOne,
            "${keyMapPrefix}_2" to vehiclesInPolygonTwo
        )

        val result = helpers.polygonWithVehiclesList(polygonWithVehicles)
        result.forEachIndexed { index, it ->
            val keyToMap = "${keyMapPrefix}_${index + 1}"
            assertTrue {
                it.id == keyToMap
            }
            assertTrue {
                it.vehicles == polygonWithVehicles[keyToMap]
            }
        }
    }
}
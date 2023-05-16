package com.api.location.infrastructure.mappers

import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class PolygonsArrayTest {

    @Test
    fun shouldLoadPolygonsFromJsonAndReturnArrayOfValues() {
        val jsonPath = "src/main/resources/polygonsSingleItem.json"
        val polygonsArray = PolygonsArray()
        val result = polygonsArray.loadFromJson(jsonPath)
        assertTrue { result.size == 1 }
        val firstItem = result.first()
        assertTrue { firstItem.id == "58a58bf685979b5415f3a39a" }
        assertTrue { firstItem.name == "0" }
        assertTrue { firstItem.legacyId == "18_92" }
        val expectedCoordinates: List<List<List<Float>>> = listOf(
            listOf(
                listOf(
                    9.1372480.toFloat(), 48.790411.toFloat()
                ),
                listOf(
                    9.137248.toFloat(), 48.790263.toFloat()
                ),
                listOf(
                    9.13695.toFloat(), 48.790263.toFloat()
                ),
                listOf(
                    9.137248.toFloat(), 48.790411.toFloat()
                )
            )
        )
        assertTrue { firstItem.geometry.coordinates.size == expectedCoordinates.size }
        assertTrue { firstItem.geometry.coordinates == expectedCoordinates }

    }
}
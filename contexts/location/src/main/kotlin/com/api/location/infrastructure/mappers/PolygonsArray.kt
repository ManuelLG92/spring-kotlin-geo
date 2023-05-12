package com.api.location.infrastructure.mappers

import com.api.location.infrastructure.dto.input.Polygons
import com.fasterxml.jackson.databind.ObjectMapper
import java.io.File

class PolygonsArray {
    companion object {
        fun loadFromJson(polygonFilePath: String): Array<Polygons> {
            val polygonsArray: Array<Polygons> = ObjectMapper().readValue(
                File(polygonFilePath),
                Array<Polygons>::class.java
            )
            return polygonsArray
        }
    }
}
package com.api.location.infrastructure.mappers

import com.api.location.infrastructure.dto.input.Polygons
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import java.io.File


@Component
class PolygonsArray {
    fun loadFromJson(polygonFilePath: String): Array<Polygons> {
        val polygonsArray: Array<Polygons> = ObjectMapper().readValue(
            File(File(polygonFilePath).absolutePath),
            Array<Polygons>::class.java
        )
        return polygonsArray
    }
}
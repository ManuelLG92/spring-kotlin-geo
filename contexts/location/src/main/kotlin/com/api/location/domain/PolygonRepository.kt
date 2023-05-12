package com.api.location.domain

import com.api.shared.application.dto.PolygonWithVehicles


interface PolygonRepository {
    fun get(id: String): Polygon
    fun getAll(): List<Polygon>
    fun getPolygonsWithVehicles(): List<PolygonWithVehicles>
}

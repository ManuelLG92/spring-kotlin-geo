package com.api.location.infrastructure.persistence

import com.api.location.infrastructure.dto.input.Vehicle
import com.api.shared.application.dto.PolygonWithVehicles
import me.piruin.geok.geometry.Point
import me.piruin.geok.geometry.Polygon
import org.springframework.stereotype.Component

@Component
class Helpers {

    fun isThePointInsideThePolygon(polygon: Polygon, point: Point): Boolean {
        return polygon.contains(point = point)
    }

    fun polygonWithVehiclesList(map: MutableMap<String, MutableList<String>>): List<PolygonWithVehicles> {
        val list = mutableListOf<PolygonWithVehicles>()
        map.forEach {
            val polygonWithVehicles = PolygonWithVehicles(
                id = it.key,
                vehicles = it.value
            )
            list.add(polygonWithVehicles)
        }
        return list
    }

    fun mapVehicleWithPolygonCollection(
        vehicle: Vehicle,
        mapResult: MutableMap<String, MutableList<String>>,
        polygonMap: Map<String, Polygon>
    ) {
        polygonMap.forEach { polygon ->
            val point = Point(
                longitude = vehicle.position.longitude.toDouble(),
                latitude = vehicle.position.latitude.toDouble()
            )
            val isInThePolygon = isThePointInsideThePolygon(polygon.value, point)
            if (isInThePolygon) {
                val currentPolygonInTheMapByKey = mapResult[polygon.key]
                val vehicleVin = vehicle.vin
                if (currentPolygonInTheMapByKey.isNullOrEmpty()) {
                    mapResult[polygon.key] = mutableListOf(vehicleVin)
                } else {
                    currentPolygonInTheMapByKey.add(vehicleVin)
                    mapResult[polygon.key] = currentPolygonInTheMapByKey
                }
            }
        }
    }
}
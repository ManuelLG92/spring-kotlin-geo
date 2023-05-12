package com.api.vehicles.domain

import com.api.shared.domain.AggregateRoot


class Position(val latitude: String, val longitude: String)

class Vehicle(
    override val id: String,
    val locationId: String,
    val vin: String,
    val numberPlate: String,
    val position: Position,
    val fuel: String,
    val model: String,
    private var polygon: String? = null,
) : AggregateRoot(id) {
    fun setPolygon(id: String) {
        polygon = id
    }

    fun getPolygon(): String {
        return polygon.orEmpty()
    }

}
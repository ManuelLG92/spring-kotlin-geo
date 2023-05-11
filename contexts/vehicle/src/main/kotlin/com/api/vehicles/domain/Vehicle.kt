package com.api.vehicles.domain

import com.api.shared.domain.AggregateRoot

class Vehicle(
    override val id: String,
    val locationId: String,
    val numberPlate: String,
    val position: Position,
    val fuel: String,
    val model: String
) : AggregateRoot(id) {
}
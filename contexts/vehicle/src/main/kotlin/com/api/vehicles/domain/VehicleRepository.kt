package com.api.vehicles.domain

interface VehicleRepository {
    fun getAll(): List<Vehicle>
}

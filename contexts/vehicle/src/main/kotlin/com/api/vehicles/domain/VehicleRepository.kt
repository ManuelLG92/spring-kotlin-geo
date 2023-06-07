package com.api.vehicles.domain

interface VehicleRepository {
    fun getAll(): List<Vehicle>
    fun get(id: String): Vehicle
}

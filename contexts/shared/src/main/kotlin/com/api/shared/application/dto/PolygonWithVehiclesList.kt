package com.api.shared.application.dto

data class PolygonWithVehicles(val id: String, val vehicles: List<String>)

data class PolygonWithVehiclesList(val data: List<PolygonWithVehicles>)
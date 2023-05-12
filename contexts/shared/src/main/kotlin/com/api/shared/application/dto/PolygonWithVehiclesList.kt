package com.api.shared.application.dto

import com.api.shared.infrastructure.dto.ListResponse

data class PolygonWithVehicles(val id: String, val vehicles: List<String>)

data class PolygonWithVehiclesList(override val data: List<PolygonWithVehicles>) : ListResponse<PolygonWithVehicles>

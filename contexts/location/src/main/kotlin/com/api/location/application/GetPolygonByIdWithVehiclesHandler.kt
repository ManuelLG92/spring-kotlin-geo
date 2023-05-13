package com.api.location.application

import com.api.location.domain.PolygonRepository
import com.api.shared.application.dto.PolygonWithVehicles
import com.api.shared.application.helpers.UseCaseHandler
import org.springframework.stereotype.Component

@Component
class GetPolygonByIdWithVehiclesHandler(private val repository: PolygonRepository) :
    UseCaseHandler<String, PolygonWithVehicles> {
    override fun execute(data: String): PolygonWithVehicles {
        val polygonWithVehicle = repository.getPolygonByIDWithVehicles(id = data)
        return polygonWithVehicle
    }

}
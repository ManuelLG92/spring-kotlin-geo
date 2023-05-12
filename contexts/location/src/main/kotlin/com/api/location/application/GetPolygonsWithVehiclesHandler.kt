package com.api.location.application

import com.api.shared.application.helpers.UseCaseHandlerWithoutParameters
import com.api.location.domain.PolygonRepository
import com.api.shared.application.dto.PolygonWithVehicles
import org.springframework.stereotype.Component

@Component
class GetPolygonsWithVehiclesHandler(private val repository: PolygonRepository) :
    UseCaseHandlerWithoutParameters<List<PolygonWithVehicles>> {
    override fun execute(): List<PolygonWithVehicles> {
        val polygonWithVehiclesList = repository.getPolygonsWithVehicles()
        return polygonWithVehiclesList
    }

}
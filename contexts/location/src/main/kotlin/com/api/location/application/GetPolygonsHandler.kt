package com.api.location.application

import com.api.shared.application.helpers.UseCaseHandlerWithoutParameters
import com.api.location.domain.Polygon
import com.api.location.domain.PolygonRepository
import org.springframework.stereotype.Component

@Component
class GetPolygonsHandler(private val repository: PolygonRepository) : UseCaseHandlerWithoutParameters<List<Polygon>> {
    override fun execute(): List<Polygon> {
        val polygons = repository.getAll()
        return polygons
    }

}
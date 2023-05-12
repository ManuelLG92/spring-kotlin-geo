package com.api.location.infrastructure.rest


import com.api.location.application.GetPolygonsWithVehiclesHandler
import com.api.shared.application.helpers.dto.PolygonWithVehiclesList
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetPolygonsWithVehiclesController {

    @Autowired
    private lateinit var handler: GetPolygonsWithVehiclesHandler

    @GetMapping("polygons/vehicles")
    fun polygonsWithVehicles(): ResponseEntity<PolygonWithVehiclesList> {
        val result = handler.execute()
        val response = PolygonWithVehiclesList(data = result)
        return ResponseEntity.ok(response)
    }
}
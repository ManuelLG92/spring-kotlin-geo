package com.api.location.infrastructure.rest


import com.api.location.application.GetPolygonByIdWithVehiclesHandler
import com.api.location.application.GetPolygonsWithVehiclesHandler
import com.api.shared.application.dto.PolygonWithVehicles
import com.api.shared.application.dto.PolygonWithVehiclesList
import com.api.shared.infrastructure.dto.Response
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class GetPolygonsWithVehiclesController {

    @Autowired
    private lateinit var getPolygonsWithVehiclesHandler: GetPolygonsWithVehiclesHandler

    @Autowired
    private lateinit var getPolygonByIdWithVehiclesHandler: GetPolygonByIdWithVehiclesHandler

    @GetMapping("polygons/vehicles")
    fun polygonsWithVehicles(): ResponseEntity<PolygonWithVehiclesList> {
        val result = getPolygonsWithVehiclesHandler.execute()
        val response = PolygonWithVehiclesList(data = result)
        return ResponseEntity.ok(response)
    }

    @GetMapping("polygons/{id}/vehicles/")
    fun getById(@PathVariable id: String): ResponseEntity<Response<PolygonWithVehicles>> {
        val dto = getPolygonByIdWithVehiclesHandler.execute(data = id)
        val responseDto = Response(data = dto)
        println(responseDto)
        return ResponseEntity.ok(responseDto)
    }
}
package com.api.vehicles.infrastructure.rest


import com.api.vehicles.application.GetVehiclesHandler
import com.api.vehicles.domain.Vehicle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetVehiclesController {

    @Autowired
    private lateinit var handler: GetVehiclesHandler

    @GetMapping("vehicles")
    fun index(): ResponseEntity<List<Vehicle>> {
        val dto = handler.execute()
        return ResponseEntity.ok(dto)
    }
}
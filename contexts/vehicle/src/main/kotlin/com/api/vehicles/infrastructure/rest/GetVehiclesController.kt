package com.api.vehicles.infrastructure.rest


import com.api.shared.infrastructure.dto.ListResponse
import com.api.shared.infrastructure.dto.Response
import com.api.vehicles.application.GetVehiclesByVinHandler
import com.api.vehicles.application.GetVehiclesHandler
import com.api.vehicles.domain.Vehicle
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController


@RestController
class GetVehiclesController {

    @Autowired
    private lateinit var handler: GetVehiclesHandler

    @Autowired
    
    private lateinit var handler2: GetVehiclesByVinHandler

    @GetMapping("vehicles")
    fun index(): ResponseEntity<ListResponse<Vehicle>> {
        val dto = handler.execute()
        val responseDto = ListResponse(data = dto)
        println(responseDto)
        return ResponseEntity.ok(responseDto)
    }

    @GetMapping("vehicles/{vin}")
    fun getByVin(@PathVariable vin: String): ResponseEntity<Response<Vehicle>> {
        val dto = handler2.execute(vin)
        val responseDto = Response(data = dto)
        println(responseDto)
        return ResponseEntity.ok(responseDto)
    }
}
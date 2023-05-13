package com.api.vehicles.application

import com.api.shared.application.helpers.UseCaseHandler
import com.api.vehicles.domain.Vehicle
import com.api.vehicles.domain.VehicleRepository
import org.springframework.stereotype.Component

@Component
class GetVehiclesByVinHandler(private val repository: VehicleRepository) : UseCaseHandler<String, Vehicle> {

    override fun execute(data: String): Vehicle {
        val vehicle = repository.get(id = data)
        return vehicle
    }

}
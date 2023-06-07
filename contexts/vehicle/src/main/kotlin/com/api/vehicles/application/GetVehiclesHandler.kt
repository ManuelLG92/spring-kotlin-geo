package com.api.vehicles.application

import com.api.shared.application.helpers.UseCaseHandlerWithoutParameters
import com.api.vehicles.domain.Vehicle
import com.api.vehicles.domain.VehicleRepository
import org.springframework.stereotype.Component

@Component
class GetVehiclesHandler(private val repository: VehicleRepository) : UseCaseHandlerWithoutParameters<List<Vehicle>> {

    override fun execute(): List<Vehicle> {
        val vehicles = repository.getAll()
        return vehicles
    }

}
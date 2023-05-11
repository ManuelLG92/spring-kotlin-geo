package com.api.vehicles.application

import com.api.shared.application.helpers.UseCasesHandlerEmptyParams
import com.api.vehicles.domain.Vehicle
import com.api.vehicles.domain.VehicleRepository
import org.springframework.stereotype.Component

@Component
class GetVehiclesHandler(private val repository: VehicleRepository) : UseCasesHandlerEmptyParams<List<Vehicle>> {
    override fun execute(): List<Vehicle> {
        val vehicles = repository.getAll()
        return vehicles
    }

}
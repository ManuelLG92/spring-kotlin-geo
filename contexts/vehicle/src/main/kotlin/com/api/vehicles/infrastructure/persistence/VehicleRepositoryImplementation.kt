package com.api.vehicles.infrastructure.persistence


import com.api.shared.domain.AggregateRoot
import com.api.shared.infrastructure.persistence.RepositoryImplementation
import com.api.vehicles.domain.Position
import com.api.vehicles.domain.VehicleRepository
import com.api.vehicles.domain.Vehicle
import com.fasterxml.jackson.annotation.JsonProperty
import org.slf4j.LoggerFactory
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject


data class PositionDto(@JsonProperty("latitude") val latitude: String, @JsonProperty("longitude") val longitude: String)

data class VehicleDto(
    @JsonProperty("id") override val id: String,
    @JsonProperty("locationId") val locationId: String,
    @JsonProperty("vin") val vin: String,
    @JsonProperty("numberPlate") val numberPlate: String,
    @JsonProperty("position") val position: PositionDto,
    @JsonProperty("fuel") val fuel: String,
    @JsonProperty("model") val model: String
) : AggregateRoot(id = id)

data class ResponseDto(@JsonProperty("data") val data: List<VehicleDto>)

@Component
class VehicleRepositoryImplementationImplementation : VehicleRepository,
    RepositoryImplementation<Vehicle>(Vehicle::class.simpleName.orEmpty()) {
    private val restTemplate = RestTemplateBuilder().build()
    private val url: String =
        "https://web-chapter-coding-challenge-api-eu-central-1.dev.architecture.ridedev.io/api/architecture/web-chapter-coding-challenge-api/vehicles/Stuttgart"

    private val logger = LoggerFactory.getLogger(VehicleRepository::class.toString())

    init {
        val data = getVehicles()
        updateVehicles(data)
        logger.info("Vehicles fetched")
    }

    private final fun getVehicles(): List<VehicleDto> {
        val data = restTemplate.getForObject<ResponseDto>(url)
        return data.data;
    }

    private final fun updateVehicles(data: List<VehicleDto>) {
        val backUp = getAll()
        try {
            reset()
            data.forEach {
                val vehicle = vehicleFactory(it)
                save(vehicle)
            }
        } catch (exception: Exception) {
            backUp.forEach { save(it) }
        }

    }

    fun vehicleFactory(data: VehicleDto): Vehicle {
        return Vehicle(
            id = data.id,
            locationId = data.locationId,
            numberPlate = data.numberPlate,
            vin = data.vin,
            position = Position(
                latitude = data.position.latitude,
                longitude = data.position.longitude
            ),
            fuel = data.fuel,
            model = data.model
        )
    }
}


@Bean
fun restTemplate(builder: RestTemplateBuilder): RestTemplate? {
    return builder.build()
}
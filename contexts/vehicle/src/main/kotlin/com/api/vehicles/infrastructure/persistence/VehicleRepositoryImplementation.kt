package com.api.vehicles.infrastructure.persistence


import com.api.shared.application.dto.PolygonWithVehiclesList
import com.api.shared.domain.AggregateRoot
import com.api.shared.infrastructure.gateway.EndpointCall
import com.api.shared.infrastructure.gateway.Gateway
import com.api.shared.infrastructure.gateway.GatewayOps
import com.api.shared.infrastructure.persistence.RepositoryImplementation
import com.api.vehicles.domain.Position
import com.api.vehicles.domain.Vehicle
import com.api.vehicles.domain.VehicleRepository
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.event.EventListener
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

    @Autowired
    private lateinit var gateway: Gateway

    private val restTemplate = RestTemplateBuilder().build()
    private val url: String =
        "https://web-chapter-coding-challenge-api-eu-central-1.dev.architecture.ridedev.io/api/architecture/web-chapter-coding-challenge-api/vehicles/Stuttgart"

    @EventListener(classes = [ApplicationReadyEvent::class])
    fun handleMultipleEventsOnStartUp() {
        val data = getVehicles()
        loadVehicles(data)
    }

    private final fun getVehicles(): List<VehicleDto> {
        val data = restTemplate.getForObject<ResponseDto>(url)
        return data.data
    }


    private final fun loadVehicles(data: List<VehicleDto>) {
        val backUp = getAll()
        try {
            reset()
            data.forEach {
                val vehicle = vehicleFactory(it)
                save(vehicle)
            }
            setPolygonsToVehicles()
        } catch (exception: Exception) {
            println("error $exception")
            backUp.forEach { save(it) }
        }
    }

    private fun setPolygonsToVehicles() {
        val data = gateway.getCall<PolygonWithVehiclesList>(EndpointCall(op = GatewayOps.GET_POLYGONS_WITH_VEHICLES))
        val vehicles = getAll()
        vehicles.forEach { vehicle ->
            data.data.forEach { polygon ->
                if (polygon.vehicles.contains(vehicle.vin)) {
                    vehicle.setPolygon(polygon.id)
                }
            }
            update(vehicle)
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
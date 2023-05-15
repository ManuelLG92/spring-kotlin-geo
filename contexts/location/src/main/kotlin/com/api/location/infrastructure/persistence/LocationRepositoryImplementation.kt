package com.api.location.infrastructure.persistence


import com.api.location.domain.Polygon as PolygonDomain
import com.api.location.domain.PolygonRepository
import com.api.location.infrastructure.dto.input.Polygons
import com.api.location.infrastructure.dto.input.Vehicle
import com.api.shared.application.dto.PolygonWithVehicles
import com.api.location.infrastructure.mappers.PolygonsArray
import com.api.shared.infrastructure.exceptions.BadRequestException
import com.api.shared.infrastructure.gateway.EndpointCall
import com.api.shared.infrastructure.gateway.Gateway
import com.api.shared.infrastructure.gateway.GatewayOps
import com.api.shared.infrastructure.persistence.RepositoryImplementation
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import me.piruin.geok.LatLng
import me.piruin.geok.geometry.Point
import me.piruin.geok.geometry.Polygon
import org.springframework.stereotype.Component


@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseDtoVehicles(@JsonProperty("data") val data: List<Vehicle>)

@Component
class PolygonRepositoryImplementationImplementation(
    private val gateway: Gateway,
    private val helpers: Helpers,
    private val polygonsArray: PolygonsArray
) : PolygonRepository,
    RepositoryImplementation<PolygonDomain>(PolygonDomain::class.toString()) {

    private final val polygonFilePath = "src/main/resources/polygons.json"
    private final val polygonMutableMap: MutableMap<String, Polygon> = mutableMapOf()


    init {
        val polygonsArrayResponse: Array<Polygons> = this.polygonsArray.loadFromJson(polygonFilePath)
        initMapPolygonFromJsonToPolygonDomainAndPersistThem(data = polygonsArrayResponse)
    }

    private final fun initMapPolygonFromJsonToPolygonDomainAndPersistThem(data: Array<Polygons>) {
        data.forEach {
            val coordinatesList: MutableList<LatLng> = mutableListOf()
            val coordinates = it.geometry.coordinates[0]
            coordinates.forEach { coordinate ->
                val point = LatLng(coordinate[1].toDouble(), coordinate[0].toDouble())
                coordinatesList.add(point)
            }
            polygonMutableMap[it.id] = Polygon(coordinatesList)
            val polygonDomain = PolygonDomain(
                id = it.id,
                name = it.name,
                legacyId = it.legacyId,
                geometry = it.geometry
            )

            save(polygonDomain)
        }
    }

    override fun getPolygonsWithVehicles(): List<PolygonWithVehicles> {
        val vehicles = getVehicles()
        val polygonWithVehiclesMutableMap: MutableMap<String, MutableList<String>> = mutableMapOf()
        vehicles.forEach {
            helpers.mapVehicleWithPolygonCollection(
                it,
                polygonWithVehiclesMutableMap,
                polygonMutableMap
            )
        }
        return helpers.polygonWithVehiclesList(polygonWithVehiclesMutableMap)
    }


    override fun getPolygonByIDWithVehicles(id: String): PolygonWithVehicles {
        val polygon = get(id = id)
        val polygonCalculated = polygonMutableMap[polygon.id]
            ?: throw BadRequestException("Not found polygon ${polygon.id}")
        val vehicles = getVehicles()
        val vehiclesList = mutableListOf<String>()
        vehicles.forEach {
            val point = Point(
                longitude = it.position.longitude.toDouble(),
                latitude = it.position.latitude.toDouble()
            )
            if (helpers.isThePointInsideThePolygon(
                    polygon = polygonCalculated,
                    point = point
                )
            ) {
                vehiclesList.add(it.vin)
            }

        }
        return PolygonWithVehicles(id = id, vehicles = vehiclesList)

    }

    private final fun getVehicles(): List<Vehicle> {
        val data = gateway.getCall<ResponseDtoVehicles>(EndpointCall(op = GatewayOps.GET_VEHICLES))
        return data.data
    }

}

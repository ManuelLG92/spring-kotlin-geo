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
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@JsonIgnoreProperties(ignoreUnknown = true)
data class ResponseDtoVehicles(@JsonProperty("data") val data: List<Vehicle>)

@Component
class PolygonRepositoryImplementationImplementation : PolygonRepository,
    RepositoryImplementation<PolygonDomain>(PolygonDomain::class.toString()) {

    @Autowired
    private lateinit var gateway: Gateway

    private final val polygonFilePath = "src/main/resources/polygons.json"
    private final val polygonMutableMap: MutableMap<String, Polygon> = mutableMapOf()


    init {

        val polygonsArray: Array<Polygons> = PolygonsArray.loadFromJson(polygonFilePath)

        polygonsArray.forEach {
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
        val polygonMutableMap2: MutableMap<String, MutableList<String>> = mutableMapOf()
        vehicles.forEach { mapVehicleWithPolygonCollection(it, polygonMutableMap2) }
        print("mapVehicles size: ")
        println(polygonMutableMap2.size)
        return polygonWithVehiclesList(polygonMutableMap2)
    }


    fun polygonWithVehiclesList(map: MutableMap<String, MutableList<String>>): List<PolygonWithVehicles> {
        val list = mutableListOf<PolygonWithVehicles>()
        map.forEach {
            val polygonWithVehicles = PolygonWithVehicles(
                id = it.key,
                vehicles = it.value
            )
            list.add(polygonWithVehicles)
        }
        return list
    }

    fun mapVehicleWithPolygonCollection(vehicle: Vehicle, map: MutableMap<String, MutableList<String>>) {
        polygonMutableMap.forEach { polygonMap ->
            val point = Point(
                longitude = vehicle.position.longitude.toDouble(),
                latitude = vehicle.position.latitude.toDouble()
            )
            val isInThePolygon = isThePointInsideThePolygon(polygonMap.value, point)
            if (isInThePolygon) {
                val currentPolygonInTheMapByKey = map[polygonMap.key]
                val vehicleVin = vehicle.vin
                if (currentPolygonInTheMapByKey.isNullOrEmpty()) {
                    map[polygonMap.key] = mutableListOf(vehicleVin)
                } else {
                    currentPolygonInTheMapByKey.add(vehicleVin)
                    map[polygonMap.key] = currentPolygonInTheMapByKey
                }
            }
        }
    }

    fun isThePointInsideThePolygon(polygon: Polygon, point: Point): Boolean {
        return polygon.contains(point = point)
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
            if (isThePointInsideThePolygon(
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
        println("get vehicles data $data")
        return data.data
    }

}

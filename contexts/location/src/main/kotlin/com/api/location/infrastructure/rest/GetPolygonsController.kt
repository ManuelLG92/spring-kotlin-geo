package com.api.location.infrastructure.rest


import com.api.location.application.GetPolygonsHandler
import com.api.location.domain.Polygon
import com.api.location.application.dto.output.PolygonList
import com.api.shared.infrastructure.dto.ListResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetPolygonsController {

    @Autowired
    private lateinit var handler: GetPolygonsHandler

    @GetMapping("polygons")
    fun polygons(): ResponseEntity<ListResponse<Polygon>> {
        val result = handler.execute()
        val response = PolygonList(data = result)
        return ResponseEntity.ok(response)
    }

}
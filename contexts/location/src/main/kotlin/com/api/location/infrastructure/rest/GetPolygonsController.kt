package com.api.location.infrastructure.rest


import com.api.location.application.GetPolygonsHandler
import com.api.location.domain.Polygon
import com.api.shared.infrastructure.dto.ListResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class GetPolygonsController {

    @Autowired
    private lateinit var getPolygonsHandler: GetPolygonsHandler

    @GetMapping("polygons")
    fun polygons(): ResponseEntity<ListResponse<Polygon>> {
        val result = getPolygonsHandler.execute()
        val response = ListResponse(data = result)
        return ResponseEntity.ok(response)
    }


}
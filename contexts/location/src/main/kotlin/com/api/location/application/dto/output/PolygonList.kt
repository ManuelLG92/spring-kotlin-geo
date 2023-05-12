package com.api.location.application.dto.output

import com.api.location.domain.Polygon
import com.api.shared.infrastructure.dto.ListResponse

data class PolygonList(override val data: List<Polygon>) : ListResponse<Polygon>

package com.api.location.domain

import com.api.location.infrastructure.dto.input.Geometry
import com.api.shared.domain.AggregateRoot

class Polygon(
    override val id: String,
    val name: String,
    val legacyId: String,
    val geometry: Geometry
) : AggregateRoot(id)
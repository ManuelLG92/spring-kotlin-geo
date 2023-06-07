package com.api.location.infrastructure.dto.input

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty


@JsonIgnoreProperties(ignoreUnknown = true)
data class Polygons(
    @JsonProperty("_id") val id: String,
    @JsonProperty("name") val name: String,
    @JsonProperty("legacyId") val legacyId: String,
    @JsonProperty("geometry") val geometry: Geometry
)

data class Geometry(
    @JsonProperty("type") val type: String,
    @JsonProperty("coordinates") val coordinates: List<List<List<Float>>>
)
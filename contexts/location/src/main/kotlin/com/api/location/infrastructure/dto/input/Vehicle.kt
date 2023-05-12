package com.api.location.infrastructure.dto.input

import com.fasterxml.jackson.annotation.JsonProperty

data class Position(
    @JsonProperty("latitude") val latitude: String,
    @JsonProperty("longitude") val longitude: String
)

data class Vehicle(
    @JsonProperty("id") val id: String,
    @JsonProperty("vin") val vin: String,
    @JsonProperty("position") val position: Position
)

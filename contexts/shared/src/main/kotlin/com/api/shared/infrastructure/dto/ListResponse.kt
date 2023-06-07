package com.api.shared.infrastructure.dto


data class Response<T>(val data: T)
data class ListResponse<T>(val data: List<T>)


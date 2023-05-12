package com.api.shared.infrastructure.dto

interface ListResponse<T> {
    val data: List<T>
}
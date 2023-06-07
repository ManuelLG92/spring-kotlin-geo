package com.api.shared.infrastructure.exceptions

data class BadRequestException(override val message: String) : AppException(message = message)
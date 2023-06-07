package com.api.shared.infrastructure.exceptions

data class NotFoundException(override val message: String) : AppException(message = message)
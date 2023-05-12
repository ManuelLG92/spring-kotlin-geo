package com.api.shared.application.helpers


interface UseCaseHandlerWithoutParameters<out O> {
    fun execute(): O
}

package com.api.shared.application.helpers


interface UseCaseHandler<in I, out O> {
    fun execute(data: I): O
}

interface UseCaseHandlerWithoutParameters<out O> {
    fun execute(): O
}

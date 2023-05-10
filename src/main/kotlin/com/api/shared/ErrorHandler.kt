package com.api.shared

import com.api.shared.infrastructure.exceptions.BadRequestException
import com.api.shared.infrastructure.exceptions.NotFoundException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.util.Date

data class ErrorMessageFormat(
    var status: Int? = null,
    var timestamp: Date? = null,
    var message: String? = null,
    var details: List<String>? = null,
    var context: String? = null,
)

@ControllerAdvice
class ErrorHandler : ResponseEntityExceptionHandler() {

    override fun handleMethodArgumentNotValid(
        exception: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {
        val fieldsErrors = exception.bindingResult.fieldErrors.map { "${it.field}: ${it.defaultMessage.orEmpty()}" }
        val message = "Validation errors"
        val builtMessage = ErrorMessageFormat(
            status = status.value(),
            timestamp = Date(),
            message = message,
            details = fieldsErrors
        )
        return ResponseEntity(builtMessage, status)

    }

    @ExceptionHandler(value = [(NotFoundException::class), (BadRequestException::class)])
    fun handleNotFoundException(exception: Exception, request: WebRequest): ResponseEntity<ErrorMessageFormat> {
        var status = HttpStatus.INTERNAL_SERVER_ERROR
        if (exception is NotFoundException) {
            status = HttpStatus.NOT_FOUND
        }
        if (exception is BadRequestException) {
            status = HttpStatus.BAD_REQUEST
        }
        val builtMessage = ErrorMessageFormat(
            status = status.value(),
            timestamp = Date(),
            message = exception.message.orEmpty(),
            context = exception::class.simpleName
        )
        return ResponseEntity(builtMessage, status)
    }
}
package com.nextar.contract_repo.application.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(exception: MethodArgumentNotValidException): ResponseEntity<Any> {
        val errors = exception.bindingResult.allErrors.map { error ->
            val fieldError = error as FieldError
            mapOf(
                "field" to fieldError.field,
                "message" to fieldError.defaultMessage
            )
        }

        val body = mapOf(
            "success" to false,
            "message" to "Validation failed",
            "errors" to errors
        )

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body)
    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handleMissingParams(ex: MissingServletRequestParameterException): ResponseEntity<Map<String, Any>> {
        val paramName = ex.parameterName
        val responseBody = mapOf(
            "success" to false,
            "message" to "$paramName is required"
        )
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseBody)
    }
}
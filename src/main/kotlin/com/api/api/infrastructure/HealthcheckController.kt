package com.api.api.infrastructure

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
class HealthcheckController {

    @GetMapping("/health")
    @ResponseBody
    fun execute() = "OK"
}

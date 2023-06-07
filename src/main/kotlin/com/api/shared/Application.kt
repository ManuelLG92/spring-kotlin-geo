package com.api.shared

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@ComponentScan("com.api")
@EnableScheduling
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

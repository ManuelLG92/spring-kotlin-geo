package com.api.shared.infrastructure.gateway

import com.api.shared.infrastructure.exceptions.BadRequestException
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component
import org.springframework.web.client.RestTemplate
import org.springframework.web.client.getForObject


data class EndpointCall(
    val op: GatewayOps,
    val extraPath: String? = "",
    val query: Map<String, String> = mapOf(),
)

@Component
class Gateway {

    private final val utils = Utils()
    val restTemplate: RestTemplate = RestTemplateBuilder().build()
    val availablePaths = utils.availablePaths

    final inline fun <reified T> getCall(endpointCall: EndpointCall): T {
        if (!availablePaths.contains(endpointCall.op)) {
            throw BadRequestException(
                "Operation ${endpointCall.op.name} not allowed on execute operation. Paths allowed are <${
                    availablePaths.values.joinToString(",")
                }>"
            )
        }
        val url = Utils().buildUrl(endpointCall)
        return restTemplate.getForObject<T>(url)
    }
}


@Bean
fun restTemplate(builder: RestTemplateBuilder): RestTemplate? {
    return builder.build()
}
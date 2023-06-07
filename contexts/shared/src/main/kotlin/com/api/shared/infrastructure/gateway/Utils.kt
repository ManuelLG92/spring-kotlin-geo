package com.api.shared.infrastructure.gateway

import com.api.shared.infrastructure.exceptions.BadRequestException

class Utils {
    private val host = "http://localhost:8080"
    val availablePaths =
        mapOf(
            GatewayOps.GET_VEHICLES to "/vehicles",
            GatewayOps.GET_POLYGONS_WITH_VEHICLES to "/polygons/vehicles",
        )

    fun buildUrl(op: EndpointCall): String {
        val getUri = checkPath(op.op)
        val extraInfo = buildExtraPath(op.extraPath)
        val queryBuilt = buildQueryParameters(op.query)
        return "$host$getUri$extraInfo$queryBuilt".trim()
    }

    private fun buildExtraPath(extraPath: String?): String {
        var extraInfo = ""
        if (extraPath !== null) {
            extraInfo = if (extraPath.startsWith("/")) {
                extraPath
            } else {
                "/$extraPath"
            }
        }
        return extraInfo
    }

    private fun buildQueryParameters(query: Map<String, String>?): String {
        var queryParams = ""
        if (query !== null && query.isNotEmpty()) {
            queryParams = "?"
            query.entries.forEachIndexed { index, entry ->
                val finalAppend = if (index == query.size - 1) "" else "&"
                queryParams += "${entry.key.trim()}=${entry.value.trim()}${finalAppend.trim()}"
            }
        }
        return queryParams
    }

    private fun operationNotMatched(name: String): BadRequestException {
        return BadRequestException("Operation $name not matched")
    }

    private fun checkPath(op: GatewayOps): String {
        val getPath = availablePaths[op].let {
            if (
                it.isNullOrEmpty()
            ) {
                throw operationNotMatched(op.name)
            }
            it
        }

        return getPath
    }
}

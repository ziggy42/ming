package com.serverless

import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import java.util.Collections

class ApiGatewayResponse(
    val statusCode: Int = 200,
    var body: String? = null,
    val headers: Map<String, String>? = Collections.emptyMap()
) {

    companion object {
        inline fun build(block: Builder.() -> Unit) = Builder().apply(block).build()
    }

    class Builder {
        var LOG: Logger = LogManager.getLogger(ApiGatewayResponse.Builder::class.java)
        var objectMapper = jacksonObjectMapper()

        var statusCode: Int = 200
        var headers: Map<String, String>? = Collections.emptyMap()
        var objectBody: Response? = null

        fun build(): ApiGatewayResponse {
            var body: String? = null

            if (objectBody != null) {
                try {
                    body = objectMapper.writeValueAsString(objectBody)
                } catch (e: JsonProcessingException) {
                    LOG.error("failed to serialize object", e)
                    throw RuntimeException(e)
                }
            }

            return ApiGatewayResponse(statusCode, body, headers)
        }
    }
}

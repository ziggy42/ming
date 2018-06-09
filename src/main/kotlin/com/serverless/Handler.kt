package com.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger(Handler::class.java)

class Handler : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        logger.info("received: " + input.keys.toString())

        return buildApiGatewayResponse(
            HelloResponse(
                "Go Serverless v1.x! Your Kotlin function executed successfully!",
                input
            )
        )
    }
}

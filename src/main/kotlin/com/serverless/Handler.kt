package com.serverless

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler
import com.serverless.apigateway.ApiGatewayResponse
import com.serverless.apigateway.buildApiGatewayResponse
import com.serverless.scraping.processQueries
import kotlinx.coroutines.experimental.runBlocking
import org.apache.logging.log4j.LogManager

private val logger = LogManager.getLogger(Handler::class.java)

class Handler : RequestHandler<Map<String, Any>, ApiGatewayResponse> {

    override fun handleRequest(input: Map<String, Any>, context: Context): ApiGatewayResponse {
        @Suppress("UNCHECKED_CAST")
        val queries = input["queries"] as List<String>?
        logger.info("queries: $queries")

        return buildApiGatewayResponse(runBlocking { processQueries(queries) })
    }
}
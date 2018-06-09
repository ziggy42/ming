package com.serverless

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

private val mapper = jacksonObjectMapper()

fun <T> buildApiGatewayResponse(
    body: T,
    statusCode: Int = 200,
    headers: Map<String, String>? = mapOf()
): ApiGatewayResponse = ApiGatewayResponse(statusCode, mapper.writeValueAsString(body), headers)

@Suppress("unused")
class ApiGatewayResponse(val statusCode: Int, val body: String?, val headers: Map<String, String>?)
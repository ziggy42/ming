package com.ming.scraping

import kotlinx.coroutines.experimental.delay
import kotlinx.coroutines.experimental.future.await
import kotlinx.coroutines.experimental.future.future
import java.util.concurrent.CompletableFuture

suspend fun processQueries(queries: List<String>?): Map<String, List<GoogleResult>>? =
    queries
        ?.map { it to query(it) }
        ?.map { it.first to it.second.await() }
        ?.toMap()

private fun query(query: String): CompletableFuture<List<GoogleResult>> = future {
    delay((Math.random() * 1000).toLong())
    getGoogleResults(query)
}
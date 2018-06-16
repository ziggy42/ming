package com.ming.scraping

import kotlinx.coroutines.experimental.newFixedThreadPoolContext
import kotlinx.coroutines.experimental.withContext
import org.jsoup.Jsoup
import java.net.URLEncoder

private val processContext =
    newFixedThreadPoolContext(Runtime.getRuntime().availableProcessors() + 10, "Scraper-Thread-Pool")

internal suspend fun getGoogleResults(query: String, numberOfResults: Int = 30): List<GoogleResult> =
    withContext(processContext) { scrapeGoogle(query, numberOfResults) }

private fun scrapeGoogle(query: String, numberOfResults: Int): List<GoogleResult> = Jsoup
    .connect(buildQueryURL(URLEncoder.encode(query, "UTF-8"), numberOfResults))
    .userAgent(USER_AGENT)
    .header(URLEncoder.encode(":authority", "UTF-8"), "www.google.com")
    .header(URLEncoder.encode(":method", "UTF-8"), "GET")
    .header(URLEncoder.encode(":scheme", "UTF-8"), "https")
    .header("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
    .ignoreHttpErrors(true)
    .timeout(0)
    .get()
    .select("div[class=rc]")
    .map {
        GoogleResult(
            it.select("h3[class=r]").select("a").attr("href"),
            it.select("h3[class=r]").text(),
            it.select("span[class=st]").text()
        )
    }

private fun buildQueryURL(query: String, numberOfResults: Int): String =
    "https://www.google.com/search?q=$query&num=$numberOfResults&sourceid=chrome&ie=UTF-8"
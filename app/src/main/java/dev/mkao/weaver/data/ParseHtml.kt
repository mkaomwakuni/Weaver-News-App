package dev.mkao.weaver.data

import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import java.io.IOException

fun fetchFullArticleContent(url: String): String {
    val client = OkHttpClient()
    val request = Request.Builder().url(url).build()

    return try {
        val response = client.newCall(request).execute()
        val html = response.body?.string() ?: ""
        val doc = Jsoup.parse(html)

        // This selector might change depending on the website structure
        val articleContent = doc.select("article").firstOrNull()?.text() ?: "Content not found"
        articleContent
    } catch (e: IOException) {
        e.printStackTrace()
        "Error fetching content"
    }
}
package dev.mkao.weaver.domain.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException
import java.security.KeyStore
import java.security.NoSuchAlgorithmException
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager


suspend fun fetchFullArticleContent(url: String): String = withContext(Dispatchers.IO) {
    val client = getSecureOkHttpClient()
    val request = Request.Builder().url(url).build()

    try {
        withTimeout(9000L) { // 9 second timeout
            val response = client.newCall(request).execute()
            val html = response.body?.string() ?: ""
            val doc = Jsoup.parse(html)

            val selectors = listOf(
                "article",
                ".post-content",
                ".entry-content",
                "#main-content",
                ".content",
                "main"
            )

            var content: Element? = null
            for (selector in selectors) {
                content = doc.select(selector).firstOrNull()
                if (content != null) break
            }

            content = content ?: doc.body()

            // Remove common non-content elements
            content?.select("header, nav, footer, .sidebar, .comments, script, style")?.remove()

            // Preserve some HTML structure
            content?.select("br")?.before("\\n")
            content?.select("p")?.before("\\n\\n")

            val formattedContent = content?.text()?.replace("\\n", "\n")?.trim() ?: ""

            val cleanedContent = formattedContent.replaceFirst(Regex("^(.*?\\n){0,5}"), "")

            // Check content length and return appropriate message
            if (cleanedContent.length > 50) cleanedContent else "Content not found or too short"
        }
    } catch (e: IOException) {
        e.printStackTrace()
        "Error fetching content: ${e.message}"
    }
}

private fun getSecureOkHttpClient(): OkHttpClient {
    val trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
    trustManagerFactory.init(null as KeyStore?)
    val trustManagers = trustManagerFactory.trustManagers

    // Use TLSv1.3 if available, otherwise fall back to TLSv1.2
    val sslContext = try {
        SSLContext.getInstance("TLSv1.3")
    } catch (e: NoSuchAlgorithmException) {
        SSLContext.getInstance("TLSv1.2")
    }
    sslContext.init(null, trustManagers, null)

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustManagers[0] as X509TrustManager)
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(6, TimeUnit.SECONDS)
        .build()
}
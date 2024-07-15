package dev.mkao.weaver.domain.model

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jsoup.Jsoup
import org.jsoup.nodes.Element
import java.io.IOException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

suspend fun fetchFullArticleContent(url: String): String = withContext(Dispatchers.IO) {
    val client = getUnsafeOkHttpClient()
    val httpsUrl = url.replace("http://", "https://")
    val request = Request.Builder().url(httpsUrl).build()

    try {
        withTimeout(5000L) { // 5 second timeout
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

            // Remove leading boilerplate text (adjust as needed)
            val cleanedContent = formattedContent.replaceFirst(Regex("^(.*?\\n){0,5}"), "")

            // Check content length and return appropriate message
            if (cleanedContent.length > 50) cleanedContent else "Content not found or too short"
        }
    } catch (e: IOException) {
        e.printStackTrace()
        "Error fetching content: ${e.message}"
    }
}

private fun getUnsafeOkHttpClient(): OkHttpClient {
    val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
        override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {}
        override fun getAcceptedIssuers() = arrayOf<X509Certificate>()
    })

    val sslContext = SSLContext.getInstance("SSL")
    sslContext.init(null, trustAllCerts, java.security.SecureRandom())

    return OkHttpClient.Builder()
        .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
        .hostnameVerifier { _, _ -> true }
        .connectTimeout(6, TimeUnit.SECONDS)
        .readTimeout(6, TimeUnit.SECONDS)
        .build()
}
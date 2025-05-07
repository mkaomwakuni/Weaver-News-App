package dev.mkao.weaver.domain.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.SystemClock
import dev.mkao.weaver.domain.repository.Repository
import dev.mkao.weaver.util.Assets
import dev.mkao.weaver.util.NotificationHelper
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject


class NewsNotificationReceiver : BroadcastReceiver() {

    @Inject
    lateinit var repository: Repository

    companion object {
        const val ACTION_CHECK_NEWS = "dev.mkao.weaver.ACTION_CHECK_NEWS"
        private const val REQUEST_CODE = 100


         // Scheduled periodic news checks

        fun scheduleNewsCheck(
            context: Context,
            intervalMinutes: Int = 120
        ) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NewsNotificationReceiver::class.java).apply {
                action = ACTION_CHECK_NEWS
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )

            alarmManager.setInexactRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                SystemClock.elapsedRealtime() + (intervalMinutes * 60 * 1000),
                (intervalMinutes * 60 * 1000).toLong(),
                pendingIntent
            )
        }


         //Cancel scheduled news checks

        fun cancelScheduledChecks(context: Context) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val intent = Intent(context, NewsNotificationReceiver::class.java).apply {
                action = ACTION_CHECK_NEWS
            }

            val pendingIntent = PendingIntent.getBroadcast(
                context,
                REQUEST_CODE,
                intent,
                PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
            )

            pendingIntent?.let {
                alarmManager.cancel(it)
                it.cancel()
            }
        }
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_CHECK_NEWS) {
            // Notification channel
            NotificationHelper.createNotificationChannel(context)

            GlobalScope.launch(Dispatchers.IO) {
                checkForNewsUpdates(context)
            }
        }
    }

     // Check for news updates and send a notification if new ones are found

    private suspend fun checkForNewsUpdates(context: Context) {
        try {
            // Get country code for local news
            val countryCode = Locale.getDefault().country.lowercase(Locale.ROOT)
            val category = "general"

            val result = repository.getTopHeadlines(countryCode, category, null)

            if (result is Assets.Success) {
                val articles = result.data

                // Get current stored articles
                val storedArticles = repository.getBookedArticles()

                if (storedArticles is Assets.Success) {
                    // Find new articles that aren't already stored
                    val existingUrls = storedArticles.data?.map { it.url }?.toSet() ?: emptySet()
                    val newArticles =
                        articles?.filter { !existingUrls.contains(it.url) } ?: emptyList()

                    if (newArticles.isNotEmpty()) {
                        val latestArticle = newArticles.first()
                        NotificationHelper.showNewsNotification(context, latestArticle)

                        repository.insertedArticle(latestArticle)
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
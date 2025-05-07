package dev.mkao.weaver

import android.app.Application
import com.google.android.material.color.DynamicColors
import dagger.hilt.android.HiltAndroidApp
import dev.mkao.weaver.domain.services.NewsNotificationReceiver
import dev.mkao.weaver.util.NotificationHelper

@HiltAndroidApp
class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()

        DynamicColors.applyToActivitiesIfAvailable(this)

        // Initialize notification channel
        NotificationHelper.createNotificationChannel(this)

        // Schedule periodic news checks
        NewsNotificationReceiver.scheduleNewsCheck(this)
    }
}
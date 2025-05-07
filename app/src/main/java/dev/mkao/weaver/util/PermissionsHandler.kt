package dev.mkao.weaver.util

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import dev.mkao.weaver.domain.services.NewsNotificationReceiver

class PermissionsHandler(private val activity: ComponentActivity) {

    private val requestPermissionLauncher: ActivityResultLauncher<String> =
        activity.registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                // Permission is granted, create notification channel and schedule news check
                NotificationHelper.createNotificationChannel(activity)
                scheduleNotifications()
            } else {
                cancelNotifications()
            }
        }

    /**
     * Schedules periodic news notifications
     */
    private fun scheduleNotifications() {
        NewsNotificationReceiver.Companion.scheduleNewsCheck(activity)
    }

    private fun cancelNotifications() {
        NewsNotificationReceiver.Companion.cancelScheduledChecks(activity)
    }

    /**
     * Requests notification permission for Android 13+ devices
     */
    fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            when {
                ContextCompat.checkSelfPermission(
                    activity,
                    Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED -> {
                }
                activity.shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS) -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }

                else -> {
                    requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }
        }
    }

}
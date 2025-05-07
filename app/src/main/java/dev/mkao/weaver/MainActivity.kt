package dev.mkao.weaver

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mkao.weaver.presentation.navigation.NewsNavGraph
import dev.mkao.weaver.presentation.ui.theme.WeaverTheme
import dev.mkao.weaver.util.PermissionsHandler

@Suppress("DEPRECATION")
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

	private lateinit var permissionsHandler: PermissionsHandler

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		installSplashScreen()

		permissionsHandler = PermissionsHandler(this)

		// Request notification permission if needed (Android 13+)
		permissionsHandler.requestNotificationPermissionIfNeeded()

		window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
		window.decorView.systemUiVisibility =
			View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
		window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
		window.statusBarColor = Color.WHITE
		setContent {
            WeaverTheme {
                val navController = rememberNavController()
                NewsNavGraph(navController = navController)
            }
		}
	}
}
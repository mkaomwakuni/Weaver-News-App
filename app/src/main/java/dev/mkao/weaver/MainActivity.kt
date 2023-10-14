package dev.mkao.weaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.mkao.weaver.ui.theme.WeaverTheme
import dev.mkao.weaver.util.NewsNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		installSplashScreen()
		setContent {
			WeaverTheme {
				val navController = rememberNavController()
				NewsNavGraph(navController = navController)
			}
		}
	}
}

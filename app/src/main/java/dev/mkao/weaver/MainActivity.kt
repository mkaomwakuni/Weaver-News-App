package dev.mkao.weaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dagger.hilt.android.AndroidEntryPoint
import dev.mkao.weaver.ui.ArticleScreen
import dev.mkao.weaver.ui.theme.WeaverTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			WeaverTheme {
				ArticleScreen()
			}
		}
	}
}

package dev.mkao.weaver

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import dev.mkao.weaver.ui.ArticleScreen
import dev.mkao.weaver.ui.theme.WeaverTheme
import dev.mkao.weaver.viewModel.ArticleScreenViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			WeaverTheme {
				val viewModel: ArticleScreenViewModel = hiltViewModel()
				ArticleScreen(
					states = viewModel.state,
					onUserEvent = viewModel::onUserEvent
				)
			}
		}
	}
}

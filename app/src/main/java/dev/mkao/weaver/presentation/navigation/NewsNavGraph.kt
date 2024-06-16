package dev.mkao.weaver.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.mkao.weaver.presentation.About.About
import dev.mkao.weaver.presentation.common.SettingsScreen
import dev.mkao.weaver.presentation.details.NewsArticleUi
import dev.mkao.weaver.presentation.home.ArticleScreen
import dev.mkao.weaver.presentation.home.TopSection
import dev.mkao.weaver.viewModels.ArticleScreenViewModel
import dev.mkao.weaver.viewModels.SharedViewModel

@Composable
fun NewsNavGraph(navController: NavHostController) {
	val sharedViewModel: SharedViewModel = hiltViewModel()

	NavHost(
		navController = navController,
		startDestination = Screen.Home.route
	) {
		composable(route = Screen.Home.route) {
			val viewModel: ArticleScreenViewModel = hiltViewModel()
			ArticleScreen(
				navController = navController,
				state = viewModel.state,
				onEvent = viewModel::onUserEvent,
				onReadFullStoryButtonClick = { article ->
					sharedViewModel.selectArticle(article)
					navController.navigate(Screen.NewsArticle.route)
				}
			)
		}
		composable(route = Screen.NewsArticle.route) {
			val article = sharedViewModel.selectedArticle.collectAsState().value
			val category = sharedViewModel.selectedCategory.collectAsState().value ?: "None"
			article?.let {
				NewsArticleUi (
					article = article,
					category = category,
					onBackPressed = { navController.navigateUp() }
				)
			}
		}
		composable(route = Screen.Categories.route) {
			val viewModel: ArticleScreenViewModel = hiltViewModel()
			TopSection(
				state = viewModel.state,
				navController = navController,
				onReadFullStoryButtonClick = { /* provide the correct implementation */ },
				onEvent = { /* provide the correct implementation */ }
			)
		}
		composable(route = Screen.Bookmarks.route) {
			//BookmarksScreen(onRemoveBookmark = {}, onArticleClick = {}, bookmarkedArticles = {})
		}
		composable(route = Screen.Settings.route) {
			SettingsScreen(navController)
		}
		composable(Screen.About.route) {
			About()
		}
	}
}

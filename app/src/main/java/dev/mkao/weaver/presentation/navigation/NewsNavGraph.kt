package dev.mkao.weaver.presentation.navigation

import SettingsScreen
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import dev.mkao.weaver.presentation.about.AboutMe
import dev.mkao.weaver.presentation.bookmarks.BookmarkScreen
import dev.mkao.weaver.presentation.country.CountrySelectorScreen
import dev.mkao.weaver.presentation.details.NewsArticleUi
import dev.mkao.weaver.presentation.home.ArticleScreen
import dev.mkao.weaver.presentation.home.TopSection
import dev.mkao.weaver.presentation.languageedition.LanguageEditionsSidebar
import dev.mkao.weaver.viewModels.ArticleScreenViewModel
import dev.mkao.weaver.viewModels.SharedViewModel

@Composable
fun NewsNavGraph(navController: NavHostController) {
	val sharedViewModel: SharedViewModel = hiltViewModel()

	NavHost(
		navController = navController,
		startDestination = Screen.Categories.route
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
			article?.let {
				NewsArticleUi(
					article = article,
					onBackPressed = { navController.navigateUp() }
				)
			}
		}
		composable(route = Screen.Categories.route) {
			val viewModel: ArticleScreenViewModel = hiltViewModel()
			val viewModel2: SharedViewModel = hiltViewModel()
			TopSection(
				navController = navController,
				state = viewModel.state,
				onEvent = viewModel::onUserEvent,
				sharedViewModel = viewModel2,
				onReadFullStoryButtonClick = { article ->
					sharedViewModel.selectArticle(article)
					navController.navigate(Screen.NewsArticle.route)
				}
			)
		}
		composable(route = Screen.Bookmarks.route) {
			val bookmarkViewModel: SharedViewModel = hiltViewModel()
			BookmarkScreen(sharedViewModel = bookmarkViewModel, navController)
		}
		composable(route = Screen.Settings.route) {
			SettingsScreen(navController)
		}
		composable(Screen.About.route) {
			AboutMe()
		}
		composable(route = Screen.CountrySelector.route) {
			CountrySelectorScreen(
				navController = navController,
				sharedViewModel = hiltViewModel()
			)
		}
		composable(route = Screen.LanguageEdition.route) {
			LanguageEditionsSidebar (
				navController = navController,
				onLanguageSelected = { language ->},
				onDismiss = { navController.navigateUp() }
			)
		}
	}
}

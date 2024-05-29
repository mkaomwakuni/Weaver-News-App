package dev.mkao.weaver.presentation.navgraph

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.mkao.weaver.presentation.About.About
import dev.mkao.weaver.presentation.Bookmarks.Screen
import dev.mkao.weaver.presentation.Bookmarks.SettingsScreen
import dev.mkao.weaver.presentation.details.components.NewsArticleUi
import dev.mkao.weaver.presentation.home.ArticleScreen
import dev.mkao.weaver.presentation.home.ArticleScreenViewModel

@Composable
fun NewsNavGraph(
	navController: NavHostController
){
	val argKey = "web_url"

	NavHost(
		navController = navController,
		startDestination = Screen.Home.route
	) {
		composable(route = Screen.Home.route) {
			val viewModel: ArticleScreenViewModel = hiltViewModel()
			ArticleScreen(
				navController = navController,
				state = viewModel.state,
				onEvent =  viewModel::onUserEvent,
				onReadFullStoryButtonClick = { url ->
					navController.navigate("${Screen.NewsArticle.route}?$argKey=$url")
				}
			)
		}
		composable(
			route = "${Screen.NewsArticle.route}?$argKey={$argKey}",
			arguments = listOf(navArgument(name = argKey) {
				type = NavType.StringType
			})
		) { backStackEntry ->
			NewsArticleUi(
				url = backStackEntry.arguments?.getString(argKey),
				onBackPressed = { navController.navigateUp() }
			)
		}
		composable(route = Screen.Categories.route) {
			//CategoriesScreen()
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

package dev.mkao.weaver.util

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.mkao.weaver.ui.ArticleScreen
import dev.mkao.weaver.ui.article.NewsArticleUi
import dev.mkao.weaver.viewModel.ArticleScreenViewModel

@Composable
fun NewsNavGraph(
	navController: NavHostController
){
	val argKey = "web_url"
	
	NavHost(
		navController = navController,
		startDestination = "article_screen"
	) {
		composable(route = "article_screen") {
			val viewModel: ArticleScreenViewModel = hiltViewModel()
			ArticleScreen(
				states = viewModel.state,
				onUserEvent =  viewModel::onUserEvent,
				onReadFullStoryButtonClick = { url ->
					navController.navigate("news_article?$argKey=$url")
				}
			)
			
		}
		composable(
			route = "news_article?$argKey={$argKey}",
			arguments = listOf(navArgument(name = argKey) {
				type = NavType.StringType
			})
		) { backStackEntry ->
			NewsArticleUi(
				url = backStackEntry.arguments?.getString(argKey),
				onBackPressed = { navController.navigateUp() }
			)
		}
	}
}
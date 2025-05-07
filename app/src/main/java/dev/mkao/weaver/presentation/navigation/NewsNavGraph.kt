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
import dev.mkao.weaver.presentation.bookmarks.BookmarkViewModel
import dev.mkao.weaver.presentation.details.DetailViewModel
import dev.mkao.weaver.presentation.details.NewsArticleUi
import dev.mkao.weaver.presentation.details.state.DetailEvent
import dev.mkao.weaver.presentation.home.ArticleScreen
import dev.mkao.weaver.presentation.home.ArticleViewModel
import dev.mkao.weaver.presentation.home.DashBoard
import dev.mkao.weaver.presentation.languages.LanguageEditionsSidebar

@Composable
fun NewsNavGraph(
	navController: NavHostController
) {
	val articleViewModel: ArticleViewModel = hiltViewModel()
	val detailViewModel: DetailViewModel = hiltViewModel()
	val bookmarkViewModel: BookmarkViewModel = hiltViewModel()

	NavHost(
		navController = navController,
		startDestination = Screen.Categories.route
	) {
		composable(route = Screen.Home.route) {
			val state = articleViewModel.state.collectAsState().value
			ArticleScreen(
				navController = navController,
				state = state,
				onEvent = articleViewModel::onEvent,
				onReadFullStoryButtonClick = { article ->
					detailViewModel.onEvent(DetailEvent.ArticleSelected(article))
					navController.navigate(Screen.NewsArticle.route)
				},
				bookmarkViewModel = bookmarkViewModel
			)
		}

		composable(route = Screen.NewsArticle.route) {
			val article = detailViewModel.state.collectAsState().value.selectedArticle
			article?.let {
				NewsArticleUi(
					article = article,
					onBackPressed = { navController.navigateUp() },
					bookmarkViewModel = bookmarkViewModel
				)
			}
		}

		composable(route = Screen.Categories.route) {
			val state = articleViewModel.state.collectAsState().value
			DashBoard(
                navController = navController,
				state = state,
				onEvent = articleViewModel::onEvent,
                onReadFullStoryButtonClick = { article ->
					detailViewModel.onEvent(DetailEvent.ArticleSelected(article))
                    navController.navigate(Screen.NewsArticle.route)
                },
                articleViewModel = articleViewModel,
				bookmarkViewModel = bookmarkViewModel
            )
		}

		composable(route = Screen.Bookmarks.route) {
			BookmarkScreen(
				sharedViewModel = bookmarkViewModel,
				navController = navController
			)
		}

		composable(route = Screen.Settings.route) {
			SettingsScreen(navController = navController)
		}

		composable(route = Screen.About.route) {
			AboutMe()
		}

        composable(route = Screen.LanguageEditions.route) {
            LanguageEditionsSidebar(
                navController = navController,
                viewModel = articleViewModel,
                onLanguageSelected = { languageCode, countryCode ->
                    articleViewModel.forceRefresh()
                }
            )
        }
    }
}
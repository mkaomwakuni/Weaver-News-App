package dev.mkao.weaver.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val title: String, val icon: ImageVector) {
    object Home : Screen("article_screen", "Home", Icons.Default.Home)
    object Categories : Screen("recommendation_screen", "Bulletin", Icons.Filled.CheckCircle)
    object Bookmarks : Screen("bookmarks_screen", "Bookmarks", Icons.Default.FavoriteBorder)
    object Settings : Screen("settings_screen", "Settings", Icons.Default.Settings)
    object NewsArticle : Screen("news_article", "News Article", Icons.Default.Home)
    object About : Screen("about","About",  Icons.Filled.Info)
}

package dev.mkao.weaver.presentation.navgraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.mkao.weaver.R

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.dashboard), contentDescription = "Home") },
            label = { Text(stringResource(R.string.dashboard)) },
            selected = currentRoute == "article_screen",
            onClick = {
                navController.navigate("article_screen") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.CheckCircle, contentDescription = "Home") },
            label = { Text(stringResource(R.string.bulletin)) },
            selected = currentRoute == "recommendation_screen",
            onClick = {
                navController.navigate("recommendation_screen") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(painter = painterResource(id = R.drawable.bookmark), contentDescription = "Bookmarks") },
            label = { Text(stringResource(R.string.bookmarks)) },
            selected = currentRoute == "bookmarks_screen",
            onClick = {
                navController.navigate("bookmarks_screen") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings") },
            label = { Text(stringResource(R.string.settings)) },
            selected = currentRoute == "settings_screen",
            onClick = {
                navController.navigate("settings_screen") {
                    popUpTo(navController.graph.startDestinationId) { saveState = true }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        )
    }
}

@Preview
@Composable
fun PreviewBottomItems() {
    val navController = rememberNavController()
    BottomNavigationBar(navController = navController)
}

package dev.mkao.weaver.presentation.common

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun StatusbarEffect() {
    // Remember the system UI controller instance
    val systemUiController = rememberSystemUiController()

    // Determine if the system is in dark theme
    val isDarkTheme = isSystemInDarkTheme()

    // Decide whether to use dark icons based on the current theme
    val useDarkIcons = !isDarkTheme

    // Apply side effects for setting the system bar colors
    SideEffect {
        systemUiController.setSystemBarsColor(
            color = Color.Transparent,
            darkIcons = useDarkIcons
        )
    }
}

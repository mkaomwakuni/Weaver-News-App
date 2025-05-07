package dev.mkao.weaver.presentation.common

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


fun Modifier.shimmerEffect() = composed {
    // Define the colors for the shimmer effect
    val shimmerColors = listOf(
        Color.White.copy(alpha = 0.3f), // Start color
        MaterialTheme.colorScheme.primary.copy(alpha = 0.9f), // Mid color
        Color.White.copy(alpha = 0.3f)  // End color
    )

    // infinite transition for continuous animation
    val transition = rememberInfiniteTransition(label = "shimmer")

    // Animate a float from 0f to 1000f
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "shimmer"
    )

    // a background with a moving linear gradient
    background(
        brush = Brush.linearGradient(
            colors = shimmerColors,
            start = Offset.Zero, // Start from top-left
            end = Offset(x = translateAnim.value, y = translateAnim.value) // Move towards bottom-right
        )
    )
}

/**
 * A composable function that creates a card with a shimmer effect.
 * This can be used as a placeholder while loading actual content.
 *
 * @param modifier Modifier to be applied to the card
 */
@Composable
fun ArticleCardShimmerEffect(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp),
    ) {
        // Main content area with shimmer effect
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp)
                .clip(MaterialTheme.shapes.small)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)) // Pale background
                .shimmerEffect()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Secondary content area with shimmer effect
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.small)
                    .height(50.dp)
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .shimmerEffect()
            )
        }
    }
}

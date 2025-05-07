package dev.mkao.weaver.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article

@Composable
fun AnimatedArticleCard(
    article: Article,
    position: Int,
    progress: Float,
    onReadFullStoryClicked: (Article) -> Unit
) {
    val cardWidth = 335f
    val spacing = 1f
    val xOffset: Float
    val scale: Float

    when (position) {
        0 -> { // Outgoing article
            xOffset = -cardWidth + spacing + (progress * (cardWidth + spacing))
            scale = 0.8f + (progress * 0.2f)
        }
        1 -> { // Central article
            xOffset = spacing + (progress * (cardWidth + spacing))
            scale = 1f - (progress * 0.2f)
        }
        else -> { // Incoming article
            xOffset = cardWidth + spacing + (progress * (cardWidth + spacing))
            scale = 0.8f + (progress * 0.2f)
        }
    }

    Box(
        modifier = Modifier
            .offset(x = xOffset.dp)
            .scale(scale)
    ) {
        DashboardCard (
            article = article,
            onReadFullStoryClicked = { onReadFullStoryClicked(article) }
        )
    }
}
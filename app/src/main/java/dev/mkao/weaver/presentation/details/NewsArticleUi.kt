package dev.mkao.weaver.presentation.details

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat.startActivity
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.services.fetchFullArticleContent
import dev.mkao.weaver.presentation.common.StatusbarEffect
import dev.mkao.weaver.util.calculateElapsedTime
import dev.mkao.weaver.viewModels.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun NewsArticleUi(
    article: Article?,
    onBackPressed: () -> Unit
) {
    val viewModel: SharedViewModel = hiltViewModel()
    var articleContent by remember { mutableStateOf("Loading...") }
    StatusbarEffect()

    article?.let { news ->
        val isBookmarked by viewModel.isArticleBookmarked(news.url).collectAsState(initial = false)

        // Fetch article content asynchronously
        LaunchedEffect(news.url) {
            articleContent = withContext(Dispatchers.IO) {
                fetchFullArticleContent(news.url)
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.Black)
        ) {
            news.image?.let { imageUrl ->
                ArticleImage(
                    imageUrl = imageUrl,
                    onBackPressed = onBackPressed,
                    title = news.title,
                    publishedAt = news.publishedAt,
                    url = news.url,
                    onBookmarkClicked = { viewModel.toggleBookmark(news) },
                )
            }
            ArticleContent(
                description = articleContent,
                content = articleContent,
                sourceName = news.source.name
            )
        }
    }
}

@Composable
fun ArticleImage(
    imageUrl: String,
    onBackPressed: () -> Unit,
    title: String,
    url: String,
    publishedAt: String?,
    onBookmarkClicked: () -> Unit
) {
    val imagePainter = rememberAsyncImagePainter(imageUrl)
    val elapsedTime = calculateElapsedTime(publishedAt)
    var isBookmarked by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(500.dp)
            .background(Color.Black)
    ) {
        // Article image
        Image(
            painter = imagePainter,
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Gradient overlay
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .align(Alignment.BottomCenter)
                .background(
                    Brush.verticalGradient(
                        colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                        startY = 0f,
                        endY = 250f
                    )
                )
        )

        // Article details
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.BottomStart)
        ) {
            CategoryChip(category = "briefs")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row {
                Text(
                    text = "Trending • ",
                    fontSize = 14.sp,
                    color = Color.White
                )
                Text(
                    text = elapsedTime,
                    fontSize = 14.sp,
                    color = Color.White
                )
            }
        }

        // Top bar with actions
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.TopCenter),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            ActionButton(
                onClick = onBackPressed,
                icon = R.drawable.arrowback,
                contentDescription = "Back"
            )
            Row {
                ActionButton(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                        startActivity(context, intent, null)
                    },
                    icon = R.drawable.morehoriz,
                    contentDescription = "Share Article"
                )
                Spacer(modifier = Modifier.width(8.dp))
                ActionButton(
                    onClick = {
                        isBookmarked = !isBookmarked
                        onBookmarkClicked()
                    },
                    icon = R.drawable.bookmark,
                    contentDescription = "Bookmark",
                    tint = if (isBookmarked) Color.Yellow else Color.White
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    onClick: () -> Unit,
    icon: Int,
    contentDescription: String,
    tint: Color = Color.White
) {
    IconButton(
        onClick = onClick,
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.2f), shape = RoundedCornerShape(8.dp))
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            tint = tint
        )
    }
}

@Composable
fun ArticleContent(
    sourceName: String?,
    content: String,
    description: String?,
    modifier: Modifier = Modifier
) {
    val contentColor = if (isSystemInDarkTheme()) Color.White else Color.Black
    val finalContent = if (content == "Content not found") description ?: "" else content

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                MaterialTheme.colorScheme.surface,
                shape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp)
            )
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SourceInfo(sourceName = sourceName)
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = finalContent,
            color = contentColor,
            fontSize = 14.sp,
            textAlign = TextAlign.Justify
        )
    }
}

@Composable
fun SourceInfo(sourceName: String?) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(R.drawable.ic_logo),
            contentDescription = stringResource(R.string.source_image),
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = sourceName ?: stringResource(R.string.other_Sources),
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onSurface,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.width(8.dp))
        Image(
            painter = painterResource(id = R.drawable.bluebadge),
            contentDescription = "Badge Image",
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun CategoryChip(category: String) {
    Surface(
        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
        shape = RoundedCornerShape(16.dp)
    ) {
        Text(
            text = category,
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}
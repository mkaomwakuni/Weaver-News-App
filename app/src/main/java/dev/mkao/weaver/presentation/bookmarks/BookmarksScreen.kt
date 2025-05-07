package dev.mkao.weaver.presentation.bookmarks

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.common.BottomNavigationBar
import dev.mkao.weaver.presentation.details.DetailViewModel
import dev.mkao.weaver.presentation.details.state.DetailEvent
import dev.mkao.weaver.presentation.navigation.Screen
import dev.mkao.weaver.util.calculateElapsedTime
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs
import kotlin.math.min

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    sharedViewModel: BookmarkViewModel,
    navController: NavController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    val bookmarkState by sharedViewModel.state.collectAsState()
    val bookmarkedArticles = bookmarkState.bookmarkedArticles.sortedByDescending { it.publishedAt }
    
    LaunchedEffect(Unit) {
        sharedViewModel.fetchBookmarkedArticles()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(R.string.Favourites),
                        fontWeight = FontWeight.Normal
                    )
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            ArticleList(
                bookmarkedArticles,
                sharedViewModel,
                navController,
                detailViewModel
            )
        }
    }
}

@Composable
fun ArticleList(
    articles: List<Article>,
    bookmarkViewModel: BookmarkViewModel,
    navController: NavController,
    detailViewModel: DetailViewModel
) {

    var visibleItems by remember { mutableStateOf(articles) }

    LaunchedEffect(articles) {
        visibleItems = articles
    }

    LazyColumn {
        items(visibleItems) { article ->
            SwipeableArticleItem(
                article = article,
                onDismiss = {
                    // Remove from visible items for immediate UI update
                    visibleItems = visibleItems.filter { it.url != article.url }
                    // Then remove from bookmarks in the database
                    bookmarkViewModel.toggleBookmark(article)
                },
                onArticleClick = {
                    detailViewModel.onEvent(
                        DetailEvent.ArticleSelected(article)
                    )
                    navController.navigate(
                        Screen.NewsArticle.route
                    )
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SwipeableArticleItem(
    article: Article,
    onDismiss: () -> Unit,
    onArticleClick: () -> Unit
) {
    var offsetX by remember { mutableStateOf(0f) }
    val dismissThreshold = 250f

    val animatedOffsetX by animateFloatAsState(
        targetValue = offsetX,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        )
    )

    val alpha by animateFloatAsState(
        targetValue = 1f - (min(abs(offsetX) / dismissThreshold, 0.5f))
    )
    
    val scope = rememberCoroutineScope()

    val baseDragResistanceFactor = 0.5f

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .alpha(alpha)
            .offset(x = animatedOffsetX.dp)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    // Calculate progressive resistance
                    val progressiveResistance = 1f - (min(abs(offsetX) / 500f, 0.7f))

                    // Apply combined resistance factors
                    val effectiveResistance = baseDragResistanceFactor * progressiveResistance

                    // Update offset with resistance applied
                    offsetX += delta * effectiveResistance
                },
                onDragStopped = { velocity ->
                    scope.launch {

                        if (abs(offsetX) > dismissThreshold) {
                            delay(150)
                            onDismiss()
                        } else {
                            // Spring back to original
                            offsetX = 0f
                        }
                    }
                }
            )
    ) {
        ArticleCard(article, onArticleClick)
    }
}

@Composable
fun ArticleCard(
    article: Article,
    onArticleClick: () -> Unit
) {
    val date = article.publishedAt
    val elapsedTime = calculateElapsedTime(date)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable {
                onArticleClick()
            }
            .padding(vertical = 4.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(article.image)
                .crossfade(true)
                .build(),
            contentDescription = article.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        // Use Chip composable for category and source
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ChipSource(
                text = article.author ?: "Source",
                backgroundColor = Color.Yellow,
                textColor = Color.Black
            )

                Spacer(modifier = Modifier.width(2.dp))

            ChipSource(
                text = article.source.name?: "Source",
                backgroundColor = Color.Blue,
                textColor = Color.White
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(8.dp)
        ) {
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
                maxLines = 2,
                color = Color.White
            )
            Text(
                text = elapsedTime,
                fontSize = 12.sp,
                color = Color.White
            )
        }
    }
}

@Composable
fun ChipSource(
    text: String,
    backgroundColor: Color,
    textColor: Color
) {
    Box(
        modifier = Modifier
            .wrapContentHeight()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            textAlign = TextAlign.Center
        )
    }
}


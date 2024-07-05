package dev.mkao.weaver.presentation.home

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.EventsHolder
import dev.mkao.weaver.presentation.common.ArticleCardShimmerEffect
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.BottomNavigationBar
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.common.CardArtiCleTop
import dev.mkao.weaver.presentation.common.StatusbarEffect
import dev.mkao.weaver.viewModels.ArticleStates
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopSection(
    state: ArticleStates,
    navController: NavController,
    onReadFullStoryButtonClick: (Article) -> Unit,
    onEvent: (EventsHolder) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    var shouldBottomSheetShow by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var isLoading by remember { mutableStateOf(true) }

    StatusbarEffect()

    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState,
            content = {
                state.isSelected?.let {
                    BottomDialog(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClick(it)
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) shouldBottomSheetShow = false
                            }
                        }
                    )
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, top = 50.dp)
                    .height(60.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                title = { /* Optional title content */ },
                navigationIcon = {
                    IconButton(onClick = { /* Handle menu icon click */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Menu,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* Handle search icon click */ }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = stringResource(R.string.search)
                        )
                    }
                    IconButton(onClick = { /* Handle notification icon click */ }) {
                        Icon(
                            imageVector = Icons.Filled.Notifications,
                            contentDescription = stringResource(R.string.notifications)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.Briefing),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.view_all),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )
                }

                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        repeat(3) {
                            ArticleCardShimmerEffect(
                                modifier = Modifier
                                    .width(350.dp)
                                    .padding(horizontal = 2.dp)
                                    .align(when (it) {
                                        1 -> Alignment.CenterStart
                                        else -> Alignment.CenterEnd
                                    })
                            )
                        }
                    }
                } else {
                    AnimatedSportsArticlesCarousel(
                        articles = state.sportsArticles,
                        onReadFullStoryClicked = { article ->
                            shouldBottomSheetShow = true
                            onEvent(EventsHolder.OnArticleCardClicked(article))
                        }
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.recommendations),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = stringResource(R.string.view_all),
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = Color.Blue.copy(alpha = 0.5f)
                    )
                }

                if (isLoading) {
                    repeat(6) {
                        ArticleCardShimmerEffect(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(10.dp),
                        verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        items(state.entertainmentArticles) { article ->
                            CardArtiCle(
                                article = article,
                                onReadFullStoryClicked = {
                                    shouldBottomSheetShow = true
                                    onEvent(EventsHolder.OnArticleCardClicked(article))
                                }
                            )
                            Spacer(modifier = Modifier.height(0.5.dp))
                        }
                    }
                }
            }
        }
    )

    LaunchedEffect(true) {
        delay(4000)
        isLoading = false
    }
}
@Composable
fun AnimatedSportsArticlesCarousel(
    articles: List<Article>,
    onReadFullStoryClicked: (Article) -> Unit
) {
    if (articles.isEmpty()) {
        // Handle empty state, perhaps show a message
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            contentAlignment = Alignment.Center
        ) {
            Text("Please wait...")
        }
        return
    }

    var currentIndex by remember { mutableIntStateOf(0) }
    val animationProgress = remember { Animatable(0f) }

    LaunchedEffect(currentIndex) {
        animationProgress.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 9000)
        )
        currentIndex = (currentIndex + 1) % articles.size
        animationProgress.snapTo(0f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        for (i in 0..2.coerceAtMost(articles.size - 1)) {
            val index = (currentIndex + i) % articles.size
            val article = articles[index]

            key(article.urlToImage) {
                AnimatedArticleCard(
                    article = article,
                    position = i,
                    progress = animationProgress.value,
                    onReadFullStoryClicked = onReadFullStoryClicked
                )
            }
        }
    }
}

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
        CardArtiCleTop(
            article = article,
            onReadFullStoryClicked = { onReadFullStoryClicked(article) }
        )
    }
}
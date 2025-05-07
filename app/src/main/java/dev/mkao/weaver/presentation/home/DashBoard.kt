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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.bookmarks.BookmarkViewModel
import dev.mkao.weaver.presentation.common.AnimatedArticleCard
import dev.mkao.weaver.presentation.common.ArticleCardShimmerEffect
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.BottomNavigationBar
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.home.state.ArticleEvent
import dev.mkao.weaver.presentation.home.state.ArticleState
import dev.mkao.weaver.presentation.navigation.Screen
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(
    state: ArticleState,
    navController: NavController,
    articleViewModel: ArticleViewModel,
    bookmarkViewModel: BookmarkViewModel,
    onReadFullStoryButtonClick: (Article) -> Unit,
    onEvent: (ArticleEvent) -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    var shouldBottomSheetShow by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    var isEditionPaneOpen by remember { mutableStateOf(false) }
    val articleState by articleViewModel.state.collectAsState()
    val countryCode = articleState.selectedCountry?.code?.uppercase() ?: "US"
    val flagUrl = "https://flagsapi.com/$countryCode/flat/64.png"


    LaunchedEffect(
        state.sportsArticles, state.entertainmentArticles) {
        state.isLoading = state.sportsArticles.isEmpty() || state.entertainmentArticles.isEmpty()
        if (state.isLoading) {
            delay(2000)
            state.isLoading = false
        }
    }


    if (shouldBottomSheetShow) {
        ModalBottomSheet(
            onDismissRequest = { shouldBottomSheetShow = false },
            sheetState = sheetState,
            content = {
                state.selectedArticle?.let {
                    BottomDialog(
                        article = it,
                        onReadFullStoryButtonClicked = {
                            onReadFullStoryButtonClick(it)
                            coroutineScope.launch { sheetState.hide() }.invokeOnCompletion {
                                if (!sheetState.isVisible) shouldBottomSheetShow = false
                            }
                        },
                        bookmarkViewModel = bookmarkViewModel
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
                    .height(80.dp)
                    .clip(shape = RoundedCornerShape(12.dp)),
                title = { /* Optional title content */ },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.navigate(Screen.LanguageEditions.route)
                        }) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Outlined.Menu,
                            tint = Color.Gray,
                            contentDescription = stringResource(R.string.menu)
                        )
                    }
                },
                actions = {
                    
                    key(countryCode) {
                        IconButton(
                            modifier = Modifier
                                .clip(shape = CircleShape)
                                .size(50.dp),
                            onClick = {
                                navController.navigate(
                                    Screen.LanguageEditions.route
                                )
                            }
                        ) {
                            Box(modifier = Modifier.fillMaxSize()) {
                                AsyncImage(
                                    model = flagUrl,
                                    contentDescription = "Selected Country Flag",
                                    contentScale = ContentScale.FillBounds,
                                    modifier = Modifier
                                        .size(50.dp)
                                        .clip(shape = CircleShape)
                                        .scale(1.5f)
                                )
                            }
                        }
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
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (state.isLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(150.dp)
                        ) {
                            repeat(3) {
                                ArticleCardShimmerEffect(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(horizontal = 16.dp)
                                )
                            }
                        }
                    } else {
                        AnimatedSportsArticlesCarousel(
                            articles = state.sportsArticles,
                            onReadFullStoryClicked = { article ->
                                shouldBottomSheetShow = true
                                onEvent(ArticleEvent.ArticleSelected(article))
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
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    if (state.isLoading) {
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
                                        onEvent(ArticleEvent.ArticleSelected(article))
                                    }
                                )
                                Spacer(modifier = Modifier.height(0.5.dp))
                            }
                        }
                    }
                }
                if (isEditionPaneOpen){

                }
            }
    )
}
@Composable
fun AnimatedSportsArticlesCarousel(
    articles: List<Article>,
    onReadFullStoryClicked: (Article) -> Unit
) {
    if (articles.isEmpty()) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            contentAlignment = Alignment.Center
        ) {
            "No articles available"
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

            key(article.image) {
                AnimatedArticleCard (
                    article = article,
                    position = i,
                    progress = animationProgress.value,
                    onReadFullStoryClicked = onReadFullStoryClicked
                )
            }
        }
    }
}


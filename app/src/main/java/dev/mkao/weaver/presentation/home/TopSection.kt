package dev.mkao.weaver.presentation.home

import android.annotation.SuppressLint
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Source
import dev.mkao.weaver.presentation.common.ArticleStates
import dev.mkao.weaver.presentation.common.BottomDialog
import dev.mkao.weaver.presentation.common.CardArtiCle
import dev.mkao.weaver.presentation.common.CardArtiCleTop
import dev.mkao.weaver.presentation.common.EventsHolder
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
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    LaunchedEffect(true) {
        onEvent(EventsHolder.OnCategoryClicked("Sports"))
    }
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

    // Filter articles by category
    val sportsArticles = state.article.filter { it.source.category == "Sports" }
    val entertainmentArticles = state.article.filter { it.source.category == "Entertainment" }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        TopAppBar(
            modifier = Modifier
                .padding(10.dp)
                .height(60.dp)
                .border(
                    width = 0.dp,
                    color = Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(shape = RoundedCornerShape(12.dp)),
            title = { /* Optional title content */ },
            navigationIcon = {
                IconButton(
                    onClick = { /* Handle menu icon click */ }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Menu"
                    )
                }
            },
            actions = {
                IconButton(
                    onClick = { /* Handle search icon click */ }
                ) {
                    Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
                }
                IconButton(
                    onClick = { /* Handle notification icon click */ }
                ) {
                    Icon(
                        imageVector = Icons.Filled.Notifications,
                        contentDescription = "Notifications"
                    )
                }
            }
        )
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = "Breaking News",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            modifier = Modifier.padding(5.dp),
            contentPadding = PaddingValues(horizontal = 5.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(sportsArticles) { article ->
                CardArtiCleTop(
                    article = article,
                    onReadFullStoryClicked = {
                        shouldBottomSheetShow = true
                        onEvent(EventsHolder.OnArticleCardClicked(article))
                    }
                )
            }
        }
        Text(
            text = "Recommendation",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(entertainmentArticles) { article ->
                CardArtiCle(
                    article = article,
                    onReadFullStoryClicked = {
                        shouldBottomSheetShow = true
                        onEvent(EventsHolder.OnArticleCardClicked(article))
                        onReadFullStoryButtonClick(article)
                    }
                )
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun TopSectionPreview() {
    val sampleArticles = listOf(
        Article(
            url = "",
            source = Source(id = "", name = "", url = "", category = "Sports"),
            content = "",
            description = "",
            author = "",
            title = "Sample Sports Article Title",
            urlToImage = "https://via.placeholder.com/400",
            publishedAt = "2024-06-16T12:00:00Z"
        ),
        Article(
            url = "",
            source = Source(id = "", name = "", url = "", category = "Entertainment"),
            content = "",
            description = "",
            author = "",
            title = "Sample Entertainment Article Title",
            urlToImage = "https://via.placeholder.com/400",
            publishedAt = "2024-06-16T12:00:00Z"
        )
    )

    val state = ArticleStates(
        isLoading = false,
        category = "General",
        article = sampleArticles
    )

    val navController = rememberNavController()

    Scaffold {
        TopSection(
            state = state,
            navController = navController,
            onReadFullStoryButtonClick = { },
            onEvent = { }
        )
    }
}

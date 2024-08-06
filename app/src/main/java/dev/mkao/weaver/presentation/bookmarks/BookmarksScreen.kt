package dev.mkao.weaver.presentation.bookmarks

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mkao.weaver.R
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.common.BottomNavigationBar
import dev.mkao.weaver.util.calculateElapsedTime
import dev.mkao.weaver.viewModels.SharedViewModel

@Composable
fun ArticleList(articles: List<Article>, sharedViewModel: SharedViewModel, navController: NavController) {
    LazyColumn {
        items(articles) { article ->
            ArticleItem(article, sharedViewModel)
        }
    }
}

@Composable
fun ArticleItem(article: Article, sharedViewModel: SharedViewModel) {
    val date = article.publishedAt
    val elapsedTime = calculateElapsedTime(date)

    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
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
                    .wrapContentHeight(), // Adjust height as needed
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Yellow background for the category
                ChipSource(
                    text = article.author ?: "Source",
                    backgroundColor = Color.Yellow,
                    textColor = Color.Black
                )

                Spacer(modifier = Modifier.width(2.dp)) // Spacing between the two parts

                // Blue background for the source
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

        IconButton(
            onClick = {
                sharedViewModel.toggleBookmark(article)
            }
        ) {
            Icon(
                Icons.Default.Close,
                contentDescription = stringResource(R.string.Delete)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    sharedViewModel: SharedViewModel = viewModel(),
    navController: NavController
) {
    val bookmarkedArticles = sharedViewModel.bookmarkedArticles.collectAsState().value
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
            ArticleList(bookmarkedArticles, sharedViewModel, navController)
        }
    }
}

package dev.mkao.weaver.presentation.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Source
import dev.mkao.weaver.util.dateFormat

@Composable
fun CardArtiCleTop(
    article: Article,
    onReadFullStoryClicked: () -> Unit
) {
    var isLoading by remember { mutableStateOf(true) }
    val date = dateFormat(article.publishedAt)

    Card(
        modifier = Modifier
            .padding(1.dp)
            .height(300.dp)
            .width(550.dp)
            .clickable { onReadFullStoryClicked() }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(article.urlToImage)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.FillBounds
            )
            // Overlay gradient
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.6f)),
                            startY = 0f,
                            endY = 250f
                        )
                    )
            ) {
                Row(modifier = Modifier
                    .wrapContentSize()
                    .padding(top = 40.dp,start = 15.dp, end = 15.dp)) {
                    CategoryChip1(category = "Technology")
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 100.dp, start = 15.dp, end = 15.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Row {
                        Text(
                            text = "Trending .",
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(end = 8.dp)
                        )
                        Text(
                            text = date,
                            fontSize = 14.sp,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = article.title,
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun CardArtiCleTopPreview() {
    CardArtiCleTop(
        article = Article(
            url = "",
            source = Source(id = "", name = "", url = "", category = ""),
            content = "",
            description = "",
            author = "",
            title = "Sample Article Title",
            urlToImage = "https://via.placeholder.com/400",
            publishedAt = "2024-06-16T12:00:00Z"
        ),
        onReadFullStoryClicked = { /* Do something */ }
    )
}

package dev.mkao.weaver.presentation.Bookmarks

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.domain.model.Source

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarksScreen(
    bookmarkedArticles: List<Article>,
    onRemoveBookmark: (Article) -> Unit,
    onArticleClick: (Article) -> Unit
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Bookmarks") },
            )
        },
        containerColor = Color(0xFFF0F0F0) // Light grey background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, start = 16.dp, top = 80.dp)
        ) {
            if (bookmarkedArticles.isEmpty()) {
                Text(
                    text = "No bookmarks available",
                    fontSize = 18.sp,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                bookmarkedArticles.forEach { article ->
                    BookmarkedArticleCard(
                        article = article,
                        onRemoveBookmark = onRemoveBookmark,
                        onArticleClick = onArticleClick
                    )
                }
            }
        }
    }
}

@Composable
fun BookmarkedArticleCard(
    article: Article,
    onRemoveBookmark: (Article) -> Unit,
    onArticleClick: (Article) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onArticleClick(article) }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = article.title, fontSize = 18.sp, color = Color.Black)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = article.description ?: "", fontSize = 14.sp, color = Color.Gray)
            }
            IconButton(onClick = { onRemoveBookmark(article) }) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remove bookmark",
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview
@Composable
fun BookmarksScreenPreview() {
    val sampleArticles = listOf(
        Article(
            source = Source(id = "1", name = "Source 1"),
            author = "Author 1",
            title = "Sample Article 1",
            content = "Content of the sample article 1",
            description = "Description of the sample article 1",
            url = "https://example.com/article1",
            urlToImage = "https://example.com/image1.jpg",
            publishedAt = "2023-05-27T12:00:00Z"
        ),
        Article(
            source = Source(id = "2", name = "Source 2"),
            author = "Author 2",
            title = "Sample Article 2",
            content = "Content of the sample article 2",
            description = "Description of the sample article 2",
            url = "https://example.com/article2",
            urlToImage = "https://example.com/image2.jpg",
            publishedAt = "2023-05-27T12:00:00Z"
        )
    )

    BookmarksScreen(
        bookmarkedArticles = sampleArticles,
        onRemoveBookmark = { /* Do something on remove */ },
        onArticleClick = { /* Do something on click */ }
    )
}

@Composable
fun Source(id: String, name: String) = Source(id, name, category ="", url = "")

package dev.mkao.weaver.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.mkao.weaver.domain.model.Article

@Composable
fun SearchResultsPage(
    articles: List<Article>,
    onArticleClicked: (Article) -> Unit,
    onReadFullStoryButtonClick: (Article) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(articles) { article ->
            SearchResultItem(
                article = article,
                onArticleClicked = { onArticleClicked(article) },
                onReadFullStoryButtonClick = { onReadFullStoryButtonClick(article) }
            )
        }
    }
}

@Composable
fun SearchResultItem(
    article: Article,
    onArticleClicked: () -> Unit,
    onReadFullStoryButtonClick: () -> Unit
) {
    // Implement the layout for each UI Componet result item
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onArticleClicked() }
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            // Display necessary information from the article
            Text(
                text = article.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            // Add more details or other UI components as needed
            // ...
            // Button to read full story
            Button(
                onClick = { onReadFullStoryButtonClick() },
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(text = "Read Full Story")
            }
        }
    }
}

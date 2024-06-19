package dev.mkao.weaver.presentation.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.presentation.common.LoadingImage
import dev.mkao.weaver.util.dateFormat


@Composable
fun SearchCard(
    modifier: Modifier = Modifier,
    article: Article,
    onCardClicked: (Article) -> Unit
) {
    val date = dateFormat(article.publishedAt)
    Card(
        modifier = modifier.clickable { onCardClicked(article) }
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            LoadingImage(imageUrl = article.urlToImage)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = article.title,
                style = MaterialTheme.typography.titleMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = article.source.name ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = date,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

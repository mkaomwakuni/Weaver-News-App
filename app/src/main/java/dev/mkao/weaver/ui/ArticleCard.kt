package dev.mkao.weaver.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.ui.componet.LoadingImage

@Composable
fun ArticleCard(
	modifier: Modifier = Modifier,
	article: Article,
	onClickingCard:(Article) -> Unit
) {
	Card(modifier = modifier.clickable{onClickingCard(article)}){
		Column(modifier = Modifier.padding(10.dp)) {
			LoadingImage(imageUrl = article.urlToImage)
		Text(
			text = article.title,
		    style = MaterialTheme.typography.bodyMedium,
		    maxLines = 1,
		    overflow = TextOverflow.Ellipsis)
			Spacer(modifier = Modifier.height(5.dp))
			Row(
				modifier = Modifier.fillMaxWidth(),
			    horizontalArrangement = Arrangement.SpaceBetween) {
				Text(
					text = article.source.name ?:"",
				    style = MaterialTheme.typography.bodySmall,
				)
				Text(
					text = article.publishedAt,
					style = MaterialTheme.typography.bodySmall)
			}
		}
		
	}
}
package dev.mkao.weaver.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.mkao.weaver.domain.model.Article

@Composable
fun ArticleCard(
	modifier: Modifier = Modifier,
	article: Article,
	onClickingCard:(Article) -> Unit
) {
	Card(modifier = modifier.clickable{onClickingCard(article)}){
		Column(modifier = Modifier.padding(10.dp)) {
		Text(
			text = article.title,
		    style = MaterialTheme.typography.subtitle2)
		}
		
	}
}
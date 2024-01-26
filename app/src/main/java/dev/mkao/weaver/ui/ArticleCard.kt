package dev.mkao.weaver.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import dev.mkao.weaver.domain.model.Article
import dev.mkao.weaver.ui.componet.dateFormat

@Composable
fun ArticleCard(
	modifier: Modifier = Modifier,
	article: Article,
	onClickingCard: (Article) -> Unit
) {
	val date = dateFormat(article.publishedAt)
	val chipColor = MaterialTheme.colorScheme
	Card(
		modifier = modifier.clickable { onClickingCard(article) }
	) {
		Column(modifier = Modifier.padding(5.dp)) {
			AsyncImage(
				modifier = Modifier
					.height(140.dp) // Adjust the height as needed
					.fillMaxWidth(),
				model = ImageRequest.Builder(LocalContext.current)
					.data(article.urlToImage)
					.build(),
				contentDescription = null,
				contentScale = ContentScale.Crop
			)
			Text(
				text = article.title,
				style = MaterialTheme.typography.bodyMedium,
				maxLines = 2,
				overflow = TextOverflow.Ellipsis
			)
			Spacer(modifier = Modifier.height(5.dp))
			Row(
				modifier = Modifier.fillMaxWidth(),
				horizontalArrangement = Arrangement.SpaceBetween
			) {
				Box(
					modifier = Modifier
						.padding(1.dp)
						.height(34.dp)
						.background(chipColor.onErrorContainer, shape = RoundedCornerShape(8.dp))
						.clip(RoundedCornerShape(4.dp))
				) {
					Text(
						modifier = Modifier.padding(8.dp),
						text = article.source.name ?: "",
						color = Color.White,
						fontWeight = FontWeight.Bold,
						style = MaterialTheme.typography.bodySmall)
				}
				Text(
					text = date,
					style = MaterialTheme.typography.bodySmall
				)
			}
			article.author?.let {
				Text(
					text = it,
					style = MaterialTheme.typography.bodySmall)
			}

		}
	}
}